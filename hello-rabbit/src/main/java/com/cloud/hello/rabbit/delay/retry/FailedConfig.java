package com.cloud.hello.rabbit.delay.retry;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ys
 * @topic 重试次数超过上限后的消息处理队列 没有额外处理
 * @date 2020/2/29 20:06
 */
@Configuration
public class FailedConfig {
    /**
     * 处理业务的队列
     */
    public static final String FAILED_QUEUE="failed.queue";
    /**
     * 处理业务的交换器
     */
    public static final String FAILED_EXCHANGE="failed.exchange";
    /**
     * 处理业务的路由key
     */
    public static final String FAILED_KEY="failed.key";

    /**
     * 处理业务的交换器
     * @return
     */
    @Bean
    public DirectExchange retryFailedExchange(){
        return new DirectExchange(FAILED_EXCHANGE);
    }

    /**
     * 处理业务的队列
     * @return
     */
    @Bean
    public Queue retryFailedQueue(){
        return QueueBuilder.durable(FAILED_QUEUE)
                .build();
    }

    /**
     * 绑定处理队列的数据监听工作
     * @return
     */
    @Bean
    public Binding failedRetryBinding(){
        return BindingBuilder.bind(retryFailedQueue())
                .to(retryFailedExchange())
                .with(FAILED_KEY);
    }
}
