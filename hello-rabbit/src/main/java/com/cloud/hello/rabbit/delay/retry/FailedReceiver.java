package com.cloud.hello.rabbit.delay.retry;

import com.cloud.hello.rabbit.pojo.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 20:22
 */
@Component
public class FailedReceiver {

    @RabbitListener(queues = FailedConfig.FAILED_QUEUE,errorHandler = "retryReceiverListenerErrorHandler")
    public void receiveDirect(User user, Channel channel, Message message){
        try {
            System.out.println("FailedReceiver监听消息"+user);
            System.out.println("人工处理");
        }catch (Exception e){
            System.out.println("人工处理");
        }
    }
}
