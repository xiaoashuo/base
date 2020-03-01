package com.cloud.hello.rabbit.delay.consume;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 13:03
 */
@Component
public class DelayQueueReceive {
    public static String DELAY_PROCESS_QUEUE_NAME="delay.process.queue";

/*    @RabbitListener(queues ="delay.process.queue" )
    @RabbitHandler
    public void receive(String msg){
        System.out.println(msg);
    }*/
}
