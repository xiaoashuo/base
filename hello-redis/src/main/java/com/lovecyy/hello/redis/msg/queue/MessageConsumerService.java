package com.lovecyy.hello.redis.msg.queue;

import com.lovecyy.hello.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author ys
 * @topic
 * @date 2020/3/1 13:05
 */
@Service
public class MessageConsumerService extends Thread {
    private volatile boolean flag = true;

    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public void run() {
        try {
            User message;
            while(flag && !Thread.currentThread().isInterrupted()) {
                message = (User) redisTemplate.opsForList().rightPop("list", 5, TimeUnit.SECONDS);
                System.out.println("接收到了" + message);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
