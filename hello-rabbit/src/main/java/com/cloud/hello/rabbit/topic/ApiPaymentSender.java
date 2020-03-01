package com.cloud.hello.rabbit.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 21:26
 */
@Component
public class ApiPaymentSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void order(String msg){
        System.out.println("api.payment.order send"+msg);
        amqpTemplate.convertAndSend("paymentExchange","api.payment.order",msg);
    }

    public void orderQuery(String msg){
        System.out.println("api.payment.order.query send message"+msg);
        amqpTemplate.convertAndSend("paymentExchange","api.payment.order.query",msg);
    }

    public void queryDetailQuery(String msg){
        System.out.println("api.payment.order.detail.query send message "+msg);
        amqpTemplate.convertAndSend("paymentExchange","api.payment.order.detail.query",msg);
    }
}
