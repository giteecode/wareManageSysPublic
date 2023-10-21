package com.education.business.message;

import com.education.business.service.system.SystemMessageLogService;
import com.education.business.task.TaskManager;
import com.education.common.constants.SystemConstants;
import com.education.common.utils.ObjectUtils;
import com.education.model.entity.MessageLog;
import com.jfinal.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Date;

/**
 * 队列消息管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/3/17 21:05
 */
@Component
public class QueueManager {

    private static Logger logger = LoggerFactory.getLogger(QueueManager.class);
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private TaskManager taskManager;
    @Resource
    private SystemMessageLogService systemMessageLogService;

    private final Jackson jackson = new Jackson();



    /**
     * 发送考试提交消息通知
     */
    public void sendQueueMessage(QueueMessage queueMessage) {
        taskManager.pushTask(() -> {
            // 异步消息日志落库，提升系统性能
            MessageLog messageLog = new MessageLog();
            String id = ObjectUtils.generateUuId();
            CorrelationData correlationData = new CorrelationData();
            correlationData.setId(id);
            try {
                // 发消息之前先进行消息落库
                queueMessage.setMessageId(id); // 消息唯一标识
                messageLog.setCorrelationDataId(id);
                messageLog.setCreateDate(new Date());
                messageLog.setExchange(queueMessage.getExchange());
                messageLog.setRoutingKey(queueMessage.getRoutingKey());
                String content = jackson.toJson(queueMessage);
                messageLog.setContent(content);
                systemMessageLogService.save(messageLog);

                rabbitTemplate.convertAndSend(queueMessage.getExchange(),//RabbitMqConfig.FANOUT_EXCHANGE,
                        queueMessage.getRoutingKey(),
                        content, correlationData);
            } catch (Exception e) {
                logger.error("消息发送异常: [" + jackson.toJson(messageLog) + "]", e);
                messageLog.setStatus(SystemConstants.SEND_FAIL);
                messageLog.setFailCause(e.getMessage());
                systemMessageLogService.updateById(messageLog);
            }
        });
    }
}
