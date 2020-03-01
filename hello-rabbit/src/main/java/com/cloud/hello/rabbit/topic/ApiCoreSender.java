package com.cloud.hello.rabbit.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 21:21
 */
@Component
public class ApiCoreSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void user(String msg){
        System.out.println("api.core.user send message"+msg);
        amqpTemplate.convertAndSend("coreExchange","api.core.user",msg);
    }

    public void userQuery(String msg){
        System.out.println("api.core.user.query send message:"+msg);
        amqpTemplate.convertAndSend("coreExchange","api.core.user.query",msg);
    }
}
