package com.education.business.task;

import cn.hutool.core.collection.CollUtil;
import com.education.business.task.param.TaskParam;
import com.education.common.annotation.EventQueue;
import com.education.common.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 任务管理器
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/13 14:37
 */
public class TaskManager {

    private final Logger logger = LoggerFactory.getLogger(TaskManager.class);
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private List<TaskListener> taskListenerList;

    private final Map<String, List<TaskListener>> queueTaskListenerMap = new ConcurrentHashMap();

    public TaskManager(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        this.threadPoolTaskExecutor = threadPoolTaskExecutor;
    }

    @PostConstruct
    public void initTaskListener() {
        taskListenerList.forEach(bean -> {
            EventQueue eventQueue = bean.getClass().getAnnotation(EventQueue.class);
            if (eventQueue != null) {
                String queueName = ObjectUtils.isEmpty(eventQueue.name()) ? bean.getClass().getSimpleName() : eventQueue.name();
                List<TaskListener> queueList = queueTaskListenerMap.get(queueName);
                if (ObjectUtils.isEmpty(queueList)) {
                    queueList = new ArrayList<>();
                }
                queueList.add(bean);
                queueTaskListenerMap.put(queueName, queueList);
            }
        });
    }

    public void pushTask(TaskParam taskParam) {
        threadPoolTaskExecutor.execute(() -> {
            pushSyncQueueTask(taskParam);
        });
    }

    public void pushSyncQueueTask(TaskParam taskParam) {
        Assert.notNull(taskParam.getQueueName(), "queue name can not be null");
        String queueName = taskParam.getQueueName();
        List<TaskListener> queueList = queueTaskListenerMap.get(queueName);
        if (CollUtil.isEmpty(queueList)) {
            logger.warn("The Queue:{} Listener is Empty", queueName);
            return;
        }
        queueList.forEach(taskListener -> {
            taskListener.onMessage(taskParam);
        });
    }

    public void pushTask(Runnable runnable) {
        threadPoolTaskExecutor.execute(runnable);
    }
}
