package com.education.business.message;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.education.business.service.system.SystemMessageLogService;
import com.education.common.constants.SystemConstants;
import com.education.model.entity.MessageLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;


/**
 * @Name: 消息确认发送到交换机回调
 * @Auther: 66
 * @Date: 2019/11/20 15:49
 * @Version:2.1.0
 */
@Component("confirmCallback")
public class ConfirmCallbackImpl implements RabbitTemplate.ConfirmCallback {

    private static final Logger logger = LoggerFactory.getLogger(ConfirmCallbackImpl.class);
    @Resource
    private SystemMessageLogService systemMessageLogService;

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        logger.info("消息唯一标识: {}", correlationData.getId());
        logger.info("确认状态: {}", ack);

        String messageId = correlationData.getId();
        LambdaUpdateWrapper updateWrapper;
        if (ack) {
            updateWrapper = new LambdaUpdateWrapper<MessageLog>()
                    .set(MessageLog::getStatus,  SystemConstants.SEND_SUCCESS)
                    .eq(MessageLog::getCorrelationDataId, messageId);
        } else {

            // 消息发送失败
            logger.error("造成原因: {}", cause);
            updateWrapper = new LambdaUpdateWrapper<MessageLog>()
                    .set(MessageLog::getStatus, SystemConstants.SEND_FAIL)
                    .set(MessageLog::getFailCause, cause)
                    .eq(MessageLog::getCorrelationDataId, messageId);
        }
        systemMessageLogService.update(null, updateWrapper);
    }
}
