package com.education.business.task.param;


/**
 * 封装任务参数类
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/13 14:46
 */
public class TaskParam {

    /**
     * 任务开始时间
     */
    private final long timestamp;

    /**
     * 任务队列名称
     */
    private String queueName;

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public String getQueueName() {
        return queueName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public TaskParam(String queueName) {
        this.queueName = queueName;
        this.timestamp = System.currentTimeMillis();
    }
}
