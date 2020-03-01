package com.cloud.hello.rabbit.delay.consume;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 17:42
 */
@Component
public class DelayReceive {

    @RabbitHandler
    @RabbitListener(queues = DelayConfig.DELAY_PROCESS_QUEUE)
    public void receive(@Payload String msg){
        System.out.println("receive a delay message"+msg);
    }
}
