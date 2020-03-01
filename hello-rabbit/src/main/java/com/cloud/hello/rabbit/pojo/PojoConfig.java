package com.cloud.hello.rabbit.pojo;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 23:01
 */
@Configuration
public class PojoConfig {

    @Bean
    public Queue objectQueue(){
        return new Queue("api.object");
    }

    @Bean
    public Queue objectQueueReceive(){
        return new Queue("api.object.receive");
    }
}
