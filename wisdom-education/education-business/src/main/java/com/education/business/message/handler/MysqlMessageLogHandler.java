package com.education.business.message.handler;

import com.education.model.entity.MessageLog;
import org.springframework.stereotype.Component;

/**
 * 将日志消息保存mysql
 * @author zengjintao
 * @version 1.6.3
 * @create_at 2021年9月10日 0010 11:34
 */
@Component
public class MysqlMessageLogHandler implements MessageLogHandler {

    @Override
    public void save(MessageLog messageLog) {

    }
}
