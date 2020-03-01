package com.cloud.hello.rabbit.delay.consume;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 17:44
 */
@Service
public class DelaySender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sender(String msg){
        amqpTemplate.convertAndSend(DelayConfig.DELAY_QUEUE,msg,message -> {
            message.getMessageProperties().setExpiration("3000");
            return message;
        });
    }
}

