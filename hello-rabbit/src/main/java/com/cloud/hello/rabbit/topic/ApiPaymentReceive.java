package com.cloud.hello.rabbit.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 21:19
 */
@Component
public class ApiPaymentReceive {

    @RabbitHandler
    @RabbitListener(queues = "api.payment")
    public void order(String msg){
        System.out.println("api.payment.order receive msg"+msg);
    }
}
