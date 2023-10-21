package com.education.business.message.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author zengjintao
 * @version 1.0.6
 * @create_at 2023/4/2 10:36
 */
@ConfigurationProperties(prefix = "rabbitmq.canal")
@Component
public class MqCanalConfig extends MqQueueConfig {
}
