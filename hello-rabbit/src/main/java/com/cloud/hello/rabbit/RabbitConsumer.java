package com.cloud.hello.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "helloRabbit")
public class RabbitConsumer {
    @RabbitHandler
    public void process(String content){
        System.out.println("consumer"+content);
    }
}
