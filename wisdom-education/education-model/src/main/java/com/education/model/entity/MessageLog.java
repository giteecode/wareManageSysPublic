package com.education.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * rabbit mq 消息日志表
 * @author zengjintao
 * @version 1.0
 * @create_at 2021/1/11 14:06
 */
@TableName("message_log")
public class MessageLog extends BaseEntity<MessageLog> {

    private String content;

    @TableField("try_count")
    private Integer tryCount;

    private Integer status;
    @TableField("correlation_data_id")
    private String correlationDataId;

    @TableField("fail_send_cause")
    private String failCause;
    @TableField("consume_cause")
    private String consumeCause;

    private String exchange;

    @TableField("routing_key")
    private String routingKey;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCorrelationDataId() {
        return correlationDataId;
    }

    public void setCorrelationDataId(String correlationDataId) {
        this.correlationDataId = correlationDataId;
    }

    public String getFailCause() {
        return failCause;
    }

    public void setFailCause(String failCause) {
        this.failCause = failCause;
    }

    public String getConsumeCause() {
        return consumeCause;
    }

    public void setConsumeCause(String consumeCause) {
        this.consumeCause = consumeCause;
    }

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
}
