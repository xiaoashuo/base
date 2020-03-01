package com.cloud.hello.rabbit.seckill;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ys
 * @topic
 * @date 2020/3/1 10:11
 */
@Service
public class SeckillNotifySender {

    @Autowired
    private RabbitTemplate amqpTemplate;

    public void sender(Integer num){
        System.out.println(num);
        amqpTemplate.convertAndSend("notify.seckill",num);
    }
}
