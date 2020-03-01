package com.cloud.hello.rabbit.delay.consume;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 12:30
 */
@Configuration
public class DelayConsumeConfig {

    public static String DELAY_QUEUE_PER_MESSAGE_TTL_NAME="delay.queue.per.message.ttl";
    public static String DELAY_QUEUE_PER_QUEUE_TTL_NAME="delay.queue.per.queue.ttl";
    public static String DEAD_LETTER_EXCHANGE_NAME="dead.letter.exchange";
    public static String DELAY_PROCESS_QUEUE_NAME="delay.process.queue";
/*

    //TTL配置在消息上的缓冲队列
    @Bean
    public Queue delayQueuePerMessageTTL(){

        return QueueBuilder.durable(DELAY_QUEUE_PER_MESSAGE_TTL_NAME)
                //DLX 死信发送到的交换机
                .withArgument("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE_NAME)
                //死信携带的routing key
                .withArgument("x-dead-letter-routing-key",DELAY_PROCESS_QUEUE_NAME)
                .build();
    }


    //缓冲队列 过期时间在队列上的
    @Bean
    public Queue delayQueuePerQueueTTL(){
        return QueueBuilder.durable(DELAY_QUEUE_PER_QUEUE_TTL_NAME)
                //DLX 死信发送到的交换机
                .withArgument("x-dead-letter-exchange",DEAD_LETTER_EXCHANGE_NAME)
                //死信携带的routing key
                .withArgument("x-dead-letter-routing-key",DELAY_PROCESS_QUEUE_NAME)
                // x-message-ttl  声明队列的TTL
                .withArgument("x-message-ttl", 1000)
                .build();
    }



    //真实消费队列
    @Bean
    public Queue delayProcessQueue(){
        return QueueBuilder.durable(DELAY_PROCESS_QUEUE_NAME)
                .build();
    }

    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE_NAME);
    }

    public Binding dlxBinding(Queue delayProcessQueue,DirectExchange delayExchange){
        return BindingBuilder.bind(delayProcessQueue)
                .to(delayExchange).with(DELAY_PROCESS_QUEUE_NAME);
    }
*/


}
