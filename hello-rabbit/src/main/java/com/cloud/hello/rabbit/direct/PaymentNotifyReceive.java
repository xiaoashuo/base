package com.cloud.hello.rabbit.direct;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 20:56
 */
@Component
@RabbitListener(queues = "notify.payment")
public class PaymentNotifyReceive {

    @RabbitHandler
    public void receive(String msg) throws Exception {{
        System.out.println("notify.paymeny receive msg"+msg);
    }
    }
}
