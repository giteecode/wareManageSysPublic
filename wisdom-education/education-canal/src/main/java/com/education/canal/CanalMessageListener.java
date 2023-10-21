package com.education.canal;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.education.model.dto.CanalQueueData;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 考试消息监听器
 */
@Component
public class CanalMessageListener {

    private static final String INSERT = "INSERT";
    private static final String UPDATE = "UPDATE";
    private static final String DELETE = "DELETE";
    private static final Logger logger = LoggerFactory.getLogger(CanalMessageListener.class);

    @Resource
    private List<CanalTableListener> canalTableListenerList;

    private static Map<Class<? extends CanalTableListener>, Class> cache = new ConcurrentHashMap<>();

    private final Map<String, CanalTableListener> canalTableListenerMap = new HashMap<>();

    @PostConstruct
    public void initTableMap() {
        if (CollUtil.isEmpty(canalTableListenerList)) {
            return;
        }
        canalTableListenerList.forEach(item -> {
            if (StrUtil.isEmpty(item.getTableName())) {
                throw new RuntimeException("The CanalMessageListener getTableName Method " +
                        "Return Value Can Not Be Null or Empty");
            }
            canalTableListenerMap.put(item.getTableName(), item);
        });
    }

    /**
     * 考试提交消息消费
     *  1. 生成考试记录
     *  2. 保存学员答题记录
     *  3. 报错学员错题本
     * @param message
     * @param channel
     */
    @RabbitListener(queues = "${rabbitmq.canal.queue}")
    public void onExamCommitMessage(Message message, Channel channel) {
        String content = new String(message.getBody());
        logger.info("canal表:{}数据:{}", null, content);
        CanalQueueData canalQueueData = JSONUtil.toBean(content, CanalQueueData.class);
        String tableName = canalQueueData.getTable();
        CanalTableListener canalTableListener = canalTableListenerMap.get(tableName);
        if (canalTableListener == null) {
            basicAck(message, channel);
            return;
        }
        Class<?> clazz = getTableClass(canalTableListener);
        List<?> newClassList = JSONUtil.toList(canalQueueData.getData(), clazz);
        List<?> oldClassList = JSONUtil.toList(canalQueueData.getOld(), clazz);
        switch (canalQueueData.getType()) {
            case INSERT:
                newClassList.forEach(item -> {
                    canalTableListener.onInsert(item);
                });
                break;
            case UPDATE:
                for (int i = 0; i < newClassList.size(); i++) {
                    canalTableListener.onUpdate(newClassList.get(i), oldClassList.get(i));
                }
                break;
            case DELETE:
                for (int i = 0; i < newClassList.size(); i++) {
                    canalTableListener.onDelete(newClassList.get(i));
                }
                break;
        }
        basicAck(message, channel);
    }


    public static <T> Class<T> getTableClass(CanalTableListener canalTableListener) {
        Class<? extends CanalTableListener> handlerClass = canalTableListener.getClass();
        Class tableClass = cache.get(handlerClass);
        if (tableClass == null) {
            Type[] interfacesTypes = handlerClass.getGenericInterfaces();
            for (Type t : interfacesTypes) {
                Class c = (Class) ((ParameterizedType) t).getRawType();
                if (c.equals(CanalTableListener.class)) {
                    tableClass = (Class<T>) ((ParameterizedType) t).getActualTypeArguments()[0];
                    cache.putIfAbsent(handlerClass, tableClass);
                    return tableClass;
                }
            }
        }
        return tableClass;
    }

    private void basicAck(Message message, Channel channel) {

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            channel.basicAck(deliveryTag, true); // 告诉rabbitmq 消息已消费
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}