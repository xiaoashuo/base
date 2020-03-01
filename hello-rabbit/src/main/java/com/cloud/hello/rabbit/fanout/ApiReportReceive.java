package com.cloud.hello.rabbit.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 22:41
 */
@Component
public class ApiReportReceive {

    @RabbitHandler
    @RabbitListener(queues = "api.report.payment")
    public void payment(String msg){
        System.out.println("api.report.payment receive"+msg);
    }

    @RabbitHandler
    @RabbitListener(queues = "api.report.refund")
    public void refund(String msg){
        System.out.println("api.report.refund receive"+msg);
    }
}
