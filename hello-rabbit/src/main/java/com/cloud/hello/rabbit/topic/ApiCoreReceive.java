package com.cloud.hello.rabbit.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 21:18
 */
@Component
public class ApiCoreReceive {

    @RabbitHandler
    @RabbitListener(queues = "api.core")
    public void user(String msg){
        System.out.println("api.core receive message"+msg);
    }
}
