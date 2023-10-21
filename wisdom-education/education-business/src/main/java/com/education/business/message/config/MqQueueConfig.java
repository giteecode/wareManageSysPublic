package com.education.business.message.config;

/**
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/2 10:35
 */
public class MqQueueConfig {

    private String queue;
    private String routingKey;
    private String directExchange;

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getDirectExchange() {
        return directExchange;
    }

    public void setDirectExchange(String directExchange) {
        this.directExchange = directExchange;
    }
}
