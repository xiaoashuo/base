package com.cloud.hello.rabbit.fanout;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 22:42
 */
@Component
public class ApiReportSender {

    @Autowired
    private AmqpTemplate amqpTemplate;


    public void generateReports(String msg){
        System.out.println("api.generate.reports send message"+msg);
        amqpTemplate.convertAndSend("reportExchange","api.generate.reports",msg);
    }
}
