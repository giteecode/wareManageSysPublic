package com.education.business.job;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 定时任务配置
 */
@Configuration
public class JobConfig {

    private static final String DEFAULT_GROUP_JOB = "default_job";

    @Bean
    public JobDetail examCountJob() {
        return JobBuilder.newJob(ExamCountJob.class)
                .withIdentity(ExamCountJob.class.getSimpleName(), DEFAULT_GROUP_JOB)
                .storeDurably().build();
    }

    @Bean
    public Trigger uploadTaskTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("*/1 * * * * ?");
        return TriggerBuilder.newTrigger().forJob(examCountJob().getKey())
                // .startAt(new Date(System.currentTimeMillis() + 1000 * 60))
                .withIdentity(ExamCountJob.class.getSimpleName())
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail rabbitMqMessageJob() {
        return JobBuilder.newJob(RabbitMqMessageJob.class)
                .withIdentity(RabbitMqMessageJob.class.getSimpleName(), DEFAULT_GROUP_JOB)
                .storeDurably().build();
    }

    @Bean
    public Trigger rabbitMqMessageTaskTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 */5 * * * ?");
        return TriggerBuilder.newTrigger().forJob(rabbitMqMessageJob().getKey())
                .withIdentity(RabbitMqMessageJob.class.getSimpleName())
                .withSchedule(scheduleBuilder)
                .build();
    }

    @Bean
    public JobDetail calendarSaveJob() {
        return JobBuilder.newJob(RabbitMqMessageJob.class)
                .withIdentity(RabbitMqMessageJob.class.getSimpleName(), DEFAULT_GROUP_JOB)
                .storeDurably().build();
    }

    @Bean
    public Trigger calendarSaveTaskTrigger() {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 * * ?");
        return TriggerBuilder.newTrigger().forJob(rabbitMqMessageJob().getKey())
                .withIdentity(RabbitMqMessageJob.class.getSimpleName())
                .withSchedule(scheduleBuilder)
                .build();
    }
}
