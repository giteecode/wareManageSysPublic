package com.education.business.message.handler;

import com.education.model.entity.MessageLog;

/**
 * 消息日志处理器
 * @author zengjintao
 * @version 1.6.3
 * @create_at 2021年9月10日 0010 11:33
 */
public interface MessageLogHandler {

    void save(MessageLog messageLog);
}
