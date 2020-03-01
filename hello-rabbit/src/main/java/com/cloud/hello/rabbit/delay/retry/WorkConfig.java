package com.cloud.hello.rabbit.delay.retry;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 18:36
 */
@Configuration
public class WorkConfig {
    /**
     * 处理业务的队列
     */
    public static final String WORK_QUEUE="retry.work.queue";
    /**
     * 处理业务的交换器
     */
    public static final String WORK_EXCHANGE="retry.work.exchange";
    /**
     * 处理业务的工作key
     */
    public static final String WORK_KEY="retry.work.key";

    /**
     * 处理业务的交换器
     * @return
     */
    @Bean
    public DirectExchange retryWorkExchange(){
        return new DirectExchange(WORK_EXCHANGE);
    }

    /**
     * 处理业务的队列
     * @return
     */
    @Bean
    public Queue retryWorkQueue(){
        return QueueBuilder.durable(WORK_QUEUE).build();
    }

    /**
     * 绑定队列的数据监听工作
     * @return
     */
    @Bean
    public Binding workRetryBinding(){
        return BindingBuilder.bind(retryWorkQueue())
                .to(retryWorkExchange()).with(WORK_KEY);
    }
}
