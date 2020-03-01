package com.cloud.hello.rabbit.delay.retry;

import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.listener.exception.ListenerExecutionFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 20:32
 */
@Component(value = "retryReceiverListenerErrorHandler")
public class RetryReceiverListenerErrorHandler implements RabbitListenerErrorHandler {

    private static ConcurrentSkipListMap<Object, AtomicInteger> map = new ConcurrentSkipListMap();

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Override
    public Object handleError(Message message, org.springframework.messaging.Message<?> message1, ListenerExecutionFailedException e) throws Exception {
        System.out.println("消息接收发生了错误，错误内容："+message1.getPayload()+"错误消息:"+e.getStackTrace());
        throw new AmqpRejectAndDontRequeueException("超出次数");
    }
}
