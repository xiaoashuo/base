package com.cloud.hello.rabbit.delay.retry;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 20:00
 */
@Configuration
public class RetryConfig {
    /**
     * 重试的队列
     */
    public static final String RETRY_QUEUE="retry.queue";
    /**
     * 重试的交换器
     */
    public static final String RETRY_EXCHANGE="retry.exchange";
    /**
     * 处理业务的路由key
     */
    public static final String RETRY_KEY="retry.key";
    /**
     * 超时时间
     */
    public static final Long QUEUE_EXPIRATION=4000L;

    /**
     * 重试的交换器
     * @return
     */
    @Bean
    public DirectExchange retryExchange(){
        return new DirectExchange(RETRY_EXCHANGE);
    }

    @Bean
    public Queue retryQueue(){
        return QueueBuilder.durable(RETRY_QUEUE)
                .withArgument("x-dead-letter-exchange",WorkConfig.WORK_EXCHANGE)
                .withArgument("x-dead-letter-routing-key",WorkConfig.WORK_KEY)
                .withArgument("x-message-ttl",QUEUE_EXPIRATION)
                .build();
    }

    /**
     * 绑定处理队列的数据监听工作
     * @return
     */
    @Bean
    public Binding retryBinding(){
        return BindingBuilder.bind(retryQueue())
                .to(retryExchange())
                .with(RETRY_KEY);
    }
}
