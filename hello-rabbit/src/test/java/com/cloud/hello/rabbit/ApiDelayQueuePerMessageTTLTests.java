package com.cloud.hello.rabbit;

import com.cloud.hello.rabbit.delay.consume.DelaySender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 16:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDelayQueuePerMessageTTLTests {

    @Autowired
    DelaySender delaySender;

    @Test
    public void testDelayQueuePerMessageTTl() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        delaySender.sender("send a delay queue message");
       Thread.sleep(5000);
    }
}
