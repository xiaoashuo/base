package com.cloud.hello.rabbit.direct;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 20:57
 */
@Component
public class PaymentNotifySender {
    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sender(String msg){
        System.out.println("notify.payment send a msg"+msg);
        amqpTemplate.convertAndSend("notify.payment",msg);
    }
}
