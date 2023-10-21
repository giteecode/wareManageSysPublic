package com.education.business.message.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/2 10:34
 */
@ConfigurationProperties(prefix = "rabbitmq.exam")
@Component
public class MqExamQueueConfig extends MqQueueConfig {
}
