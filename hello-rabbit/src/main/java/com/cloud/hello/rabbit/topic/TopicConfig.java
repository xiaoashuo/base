package com.cloud.hello.rabbit.topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 21:09
 */
@Configuration
public class TopicConfig {
    @Bean
    public Queue coreQueue(){
        return new Queue("api.core");
    }

    @Bean
    public Queue paymentQueue(){
        return new Queue("api.payment");
    }

    @Bean
    public TopicExchange coreExchange(){
        return new TopicExchange("coreExchange");
    }

    @Bean
    public TopicExchange paymentExchange(){
        return new TopicExchange("paymentExchange");
    }

    @Bean
    public Binding bindingCoreExchange(Queue coreQueue,TopicExchange coreExchange){
        return BindingBuilder.bind(coreQueue).to(coreExchange).with("api.core.*");
    }

    @Bean
    public Binding bindingPaymentExchange(Queue paymentQueue,TopicExchange paymentExchange){
        return BindingBuilder.bind(paymentQueue).to(paymentExchange).with("api.payment.#");
    }
}
