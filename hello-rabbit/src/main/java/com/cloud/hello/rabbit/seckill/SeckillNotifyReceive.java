package com.cloud.hello.rabbit.seckill;

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
public class SeckillNotifyReceive {
    AtomicInteger count = new AtomicInteger(10);
    @RabbitListener(queues = "notify.seckill",containerFactory = "multiListenerContainer")
    @RabbitHandler
    public void receive(Integer num, Channel channel, Message message) throws Exception {{

        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {

            if (num<10){
                System.out.println("exec sql option receive num:"+num);
            }
            channel.basicAck(deliveryTag,true);
        } catch (IOException e) {
            e.printStackTrace();
            channel.basicReject(deliveryTag,false);
        }

    }
    }
}
