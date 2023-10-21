package com.education.business.job;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.mapper.system.MessageLogMapper;
import com.education.common.component.SpringBeanManager;
import com.education.common.constants.SystemConstants;
import com.education.model.entity.MessageLog;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/4/2 21:02
 */
public class RabbitMqMessageJob extends BaseJob {


    private static final List<Integer> status = new ArrayList() {
        {
            add(SystemConstants.SEND_RUNNING);
            add(SystemConstants.SEND_SUCCESS);
            add(SystemConstants.CONSUME_FAIL);
            add(SystemConstants.SEND_FAIL);
        }
    };

    /**
     * 每隔五分钟扫描一次消息异常记录
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        LambdaQueryWrapper queryWrapper = Wrappers.lambdaQuery(MessageLog.class)
                .in(MessageLog::getStatus, status);
        MessageLogMapper messageLogMapper = SpringBeanManager.getBean(MessageLogMapper.class);
        List<MessageLog> messageLogList = messageLogMapper.selectList(queryWrapper);
        RabbitTemplate rabbitTemplate = SpringBeanManager.getBean(RabbitTemplate.class);
        messageLogList.forEach(item -> {
            String content = item.getContent();
            int tryCount = item.getTryCount();
            if (tryCount > SystemConstants.MAX_SEND_COUNT) {
                // 超过三次系统默认消费失败，人工进行处理
                LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class)
                        .set(MessageLog::getStatus, SystemConstants.CONSUME_FAIL)
                        .eq(MessageLog::getCorrelationDataId, item.getCorrelationDataId());
                messageLogMapper.update(null, updateWrapper);
                logger.warn("消息:{}系统默认消费失败，重试次数:{}", content, tryCount);
            }
            else {
                tryCount++;
                LambdaUpdateWrapper updateWrapper = Wrappers.lambdaUpdate(MessageLog.class)
                        .set(MessageLog::getTryCount, tryCount)
                        .set(MessageLog::getUpdateDate, new Date())
                        .eq(MessageLog::getCorrelationDataId, item.getCorrelationDataId());
                messageLogMapper.update(null, updateWrapper);

                rabbitTemplate.convertAndSend(item.getExchange(),
                        item.getRoutingKey(),
                        content, new CorrelationData(item.getCorrelationDataId()));
                logger.info("消息:{}第：{}次重发", content, tryCount);
            }
        });
    }
}
