package com.education.business.task;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author zengjintao
 * @version 1.0
 * @create_at 2020/4/24 21:11
 */
@Configuration
public class TaskBeanConfig {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors(); // 获取cpu
    private static final int COUR_SIZE = CPU_COUNT * 2;
    private static final int MAX_COUR_SIZE = CPU_COUNT * 4;

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setCorePoolSize(COUR_SIZE);
        threadPoolTaskExecutor.setMaxPoolSize(MAX_COUR_SIZE);
        threadPoolTaskExecutor.setThreadNamePrefix("education-thread-pool");
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolTaskExecutor;
    }

    @Bean
    public TaskManager taskManager(ThreadPoolTaskExecutor threadPoolTaskExecutor) {
        return new TaskManager(threadPoolTaskExecutor);
    }
}
