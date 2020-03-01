package com.cloud.hello.rabbit.fanout;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 22:36
 */
@Configuration
public class FanoutConfig {

    @Bean
    public Queue reportPaymentQueue(){
        return new Queue("api.report.payment");
    }

    @Bean
    public Queue reportRefundQueue(){
        return new Queue("api.report.refund");
    }

    @Bean
    public FanoutExchange reportExchange(){
        return new FanoutExchange("reportExchange");
    }

    @Bean
    public Binding bindReportPaymentExchange(Queue reportPaymentQueue,FanoutExchange reportExchange){
        return BindingBuilder.bind(reportPaymentQueue).to(reportExchange);
    }


    @Bean
    public Binding bindRefundExchange(Queue reportRefundQueue,FanoutExchange reportExchange){
        return BindingBuilder.bind(reportRefundQueue).to(reportExchange);
    }
}
