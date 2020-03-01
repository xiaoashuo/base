package com.cloud.hello.rabbit.delay.retry;

import com.cloud.hello.rabbit.pojo.User;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 20:14
 */
@Component
public class WorkReceiver {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = WorkConfig.WORK_QUEUE,errorHandler ="retryReceiverListenerErrorHandler" )
    public void receiveDirect(User user, Channel channel, Message message){
        System.out.println("WorkReceiver 监听到消息"+user);
        try {
            Integer retry = user.getRetry();
            int id = user.getId();
            System.out.println("重试次数:"+retry);
            if (retry<3||1==id){
                user.setRetry(retry+1);
                throw new RuntimeException("进入重试");
            }
            System.out.println("消费成功");
        } catch (Exception e) {
            System.out.println("开始重试");
            if (user.getRetry()>3){
                rabbitTemplate.convertAndSend(FailedConfig.FAILED_EXCHANGE,
                        FailedConfig.FAILED_KEY,
                        user);
                System.out.println("receiver failed");
            }else{
                rabbitTemplate.convertAndSend(
                       RetryConfig.RETRY_EXCHANGE,
                        RetryConfig.RETRY_KEY,
                        user
                );

                System.out.println("receiver error");
                throw new RuntimeException("aaa");
            }
        }

    }
}
