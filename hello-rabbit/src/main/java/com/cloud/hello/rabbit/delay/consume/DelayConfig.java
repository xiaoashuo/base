package com.cloud.hello.rabbit.delay.consume;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 17:32
 */
@Configuration
public class DelayConfig {
    //延迟队列
    public static final  String DELAY_QUEUE="delay.queue";
    public static final  String DELAY_PROCESS_QUEUE="delay.process.queue";
    public static final  String DELAY_EXCHANGE="delay.exchange";

    @Bean
    public DirectExchange delayExchange(){
        return new DirectExchange(DELAY_EXCHANGE,true,false,null);
    }

    @Bean
    public Queue delayQueue(){
        Map<String,Object> args=new HashMap<>();
        args.put("x-dead-letter-exchange",DELAY_EXCHANGE);
        args.put("x-dead-letter-routing-key",DELAY_PROCESS_QUEUE);
        return new Queue(DELAY_QUEUE,true,false,false,args);
    }

    @Bean
    public Queue processQueue(){
        return new Queue(DELAY_PROCESS_QUEUE);
    }

 /*   @Bean
    public Binding delayBinding(Queue delayQueue,DirectExchange delayExchange){
        return BindingBuilder.bind(delayQueue).to(delayExchange).with(DELAY_QUEUE);
    }*/

    @Bean
    public Binding delayProcessBinding(Queue processQueue,DirectExchange delayExchange){
        return BindingBuilder.bind(processQueue).to(delayExchange).with(DELAY_PROCESS_QUEUE);
    }
}
