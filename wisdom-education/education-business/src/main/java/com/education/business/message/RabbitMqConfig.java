package com.education.business.message;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;


/**
 * RabbitMq 配置
 * @Auther: zengjintao
 * @Date: 2019/11/20 14:56
 * @Version: 1.0.3
 */
@Configuration
public class RabbitMqConfig {

    @Resource
    private RabbitTemplate.ConfirmCallback confirmCallback;
    @Resource
    private RabbitTemplate.ReturnCallback returnsCallback;


  /*  @Bean
    public Queue webSocketQueue() {
        return QueueBuilder.durable(WEB_SOCKET_QUEUE).ttl(20000).deadLetterExchange(WEB_SOCKET_DIRECT_EXCHANGE).build();
    }

    @Bean
    public DirectExchange webSocketDirectExchange() {
        return ExchangeBuilder
                .directExchange(WEB_SOCKET_DIRECT_EXCHANGE)
                .build();
    }

    @Bean
    public Binding bindingWebSocketExchange(Queue webSocketQueue, DirectExchange webSocketDirectExchange) {
        return BindingBuilder.bind(webSocketQueue).to(webSocketDirectExchange).with(WEB_SOCKET_QUEUE_ROUTING_KEY);
    }*/


/*    @Bean
    public DirectExchange sanyouDirectExchangee() {
        return new DirectExchange("sanyouDirectExchangee");
    }

    @Bean
    public Queue sanyouQueue() {
        return QueueBuilder
                //指定队列名称，并持久化
                .durable("sanyouQueue")
                //设置队列的超时时间为5秒，也就是延迟任务的时间
                .ttl(5000)
                //指定死信交换机
                .deadLetterExchange("sanyouDelayTaskExchangee")
                .build();
    }

    @Bean
    public Binding sanyouQueueBinding() {
        return BindingBuilder.bind(sanyouQueue()).to(sanyouDirectExchangee()).with("");
    }

    @Bean
    public DirectExchange sanyouDelayTaskExchange() {
        return new DirectExchange("sanyouDelayTaskExchangee");
    }

    @Bean
    public Queue sanyouDelayTaskQueue() {
        return QueueBuilder
                //指定队列名称，并持久化
                .durable("sanyouDelayTaskQueue")
                .build();
    }

    @Bean
    public Binding sanyouDelayTaskQueueBinding() {
        return BindingBuilder.bind(sanyouDelayTaskQueue()).to(sanyouDelayTaskExchange()).with("");
    }*/


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(connectionFactory);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnsCallback);
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory RabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        simpleRabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return simpleRabbitListenerContainerFactory;
    }
}
