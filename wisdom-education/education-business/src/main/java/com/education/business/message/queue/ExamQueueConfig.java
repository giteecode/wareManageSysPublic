package com.education.business.message.queue;

import com.education.business.message.config.MqExamQueueConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * 考试队列配置
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/2 10:38
 */
@Configuration
public class ExamQueueConfig {

    @Resource
    private MqExamQueueConfig mqExamQueueConfig;

    /**
     * 创建考试消息队列
     * @return
     */
    @Bean
    public Queue examQueue() {
        return new Queue(mqExamQueueConfig.getQueue());
    }


    /**
     * 交换机配置
     * @return
     */
    @Bean
    public FanoutExchange examDirectExchange() {
        return ExchangeBuilder
                .fanoutExchange(mqExamQueueConfig.getDirectExchange())
                .build();
    }

    @Bean
    public Binding bindingExamExchange(FanoutExchange examDirectExchange, Queue examQueue) {
        return BindingBuilder.bind(examQueue).to(examDirectExchange);
    }
}
