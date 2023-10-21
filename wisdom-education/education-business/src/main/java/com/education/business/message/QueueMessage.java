package com.education.business.message;

import java.io.Serializable;

/**
 * @author zengjintao
 * @version 1.6.3
 * @create_at 2021年9月10日 0010 11:18
 */
public abstract class QueueMessage implements Serializable {

    private String exchange;
    private String routingKey;
    /**
     * 消息唯一标识
     */
    private String messageId;

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
