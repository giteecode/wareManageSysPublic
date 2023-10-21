package com.education.business.message;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.education.business.service.system.SystemMessageLogService;
import com.education.common.constants.SystemConstants;
import com.education.model.entity.MessageLog;
import com.jfinal.json.Jackson;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;


/**
 * 考试消息监听器
 */
@Component

public class ExamMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(ExamMessageListener.class);

    private final Jackson jackson = new Jackson();

    @Resource
    private SystemMessageLogService systemMessageLogService;
    @Resource
    private ExamMessageListenerService examMessageListenerService;

    /**
     * 考试提交消息消费
     *  1. 生成考试记录
     *  2. 保存学员答题记录
     *  3. 报错学员错题本
     * @param message
     * @param channel
     */
    @RabbitListener(queues = "${rabbitmq.exam.queue}")
    public void onExamCommitMessage(Message message, Channel channel) {
        String content = new String(message.getBody());
        ExamMessage examMessage = jackson.parse(content, ExamMessage.class);
        String messageId = examMessage.getMessageId();
        MessageLog messageLog = systemMessageLogService.selectByMessageId(examMessage.getMessageId());
        if (messageLog == null) {
            return;
        }
        Integer status = messageLog.getStatus();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {
            if (status != SystemConstants.CONSUME_SUCCESS) {
                examMessageListenerService.doExamCommitMessageBiz(examMessage, messageId);
            }
            channel.basicAck(deliveryTag, true); // 告诉rabbitmq 消息已消费
        } catch (Exception e) {
            LambdaUpdateWrapper<MessageLog> updateWrapper = Wrappers.lambdaUpdate(MessageLog.class).set(MessageLog::getConsumeCause, e.getMessage())
                    .set(MessageLog::getStatus, SystemConstants.CONSUME_FAIL)
                    .set(MessageLog::getUpdateDate, new Date())
                            .eq(MessageLog::getCorrelationDataId, messageId);
            systemMessageLogService.update(messageLog, updateWrapper);
            logger.error("考试队列消息:{}消费失败", content, e);
            try {
                channel.basicAck(deliveryTag, true); // 告诉rabbitmq 消息已消费
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }


   /* @RabbitListener(queues = "sanyouDelayTaskQueue")
    public void onTest(Message message, Channel channel) {
        String content = new String(message.getBody());
        logger.info("延迟队列消息:{}", content);
    }*/
}
