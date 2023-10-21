package com.education.business.task;

import com.education.business.task.param.TaskParam;

/**
 * 任务监听器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/13 14:35
 */
public interface TaskListener<T extends TaskParam> {

    /**
     * 消息监听回调
     * @param taskParam
     */
    default void onMessage(T taskParam) {

    }

    /**
     * 发送email消息监听事件
     * @param taskParam
     */
    default void onEmailMessage(T taskParam) {

    }
}
