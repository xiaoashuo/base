package com.cloud.hello.rabbit.header;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 22:00
 */
@Component
public class ApiCreditReceive {

    @RabbitListener(queues = "credit.bank")
    @RabbitHandler
    public void creditBank(String msg){
        System.out.println("credit.bank receive message"+msg);
    }

    @RabbitListener(queues = "credit.finance")
    @RabbitHandler
    public void creditFinance(String msg){
        System.out.println("credit.finance receive message"+msg);
    }
}
