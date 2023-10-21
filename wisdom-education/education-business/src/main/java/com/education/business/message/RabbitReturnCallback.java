package com.education.business.message;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.education.business.mapper.system.MessageLogMapper;
import com.education.common.constants.SystemConstants;
import com.education.model.entity.MessageLog;
import com.jfinal.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.Map;


/**
 * @Name:
 *  消息消费失败回调
 * @Auther: 66
 * @Date: 2019/11/20 16:06
 * @Version:2.1.0
 */
@Component
public class RabbitReturnCallback implements RabbitTemplate.ReturnCallback {

    private static final Logger logger = LoggerFactory.getLogger(RabbitReturnCallback.class);

    @Resource
    private MessageLogMapper messageLogMapper;

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("消息主体: {}", message);
        logger.info("回复编码: {}", replyCode);
        logger.info("回复内容: {}", replyText);
        logger.info("交换器: {}", exchange);
        logger.info("路由键: {}", routingKey);

        Map result = new Jackson().parse(new String(message.getBody()), Map.class);
        String cause = "{回复编码:" + replyCode + ", 回复内容: " + replyText;
        LambdaUpdateWrapper updateWrapper = new LambdaUpdateWrapper<MessageLog>()
                .set(MessageLog::getStatus,  SystemConstants.CONSUME_FAIL)
                .set(MessageLog::getConsumeCause, cause)
                .eq(MessageLog::getCorrelationDataId, result.get("messageId"));
        messageLogMapper.update(null, updateWrapper);
    }
}
