package com.lovecyy.hello.redis.pubsub;


import com.lovecyy.hello.redis.pojo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ys
 * @topic 接收消息处理器
 * @date 2020/3/1 11:53
 */

public class Receiver {

    private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

    private AtomicInteger counter = new AtomicInteger();

    public void receiveMessage(String message) {
        LOGGER.info("Received <" + message + ">");
        counter.incrementAndGet();
    }

    public void receiveMessage2(String msg) {
        LOGGER.info("Received <" + msg + ">");
        counter.incrementAndGet();
    }


    public int getCount() {
        return counter.get();
    }
}
