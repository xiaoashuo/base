package com.lovecyy.hello.redis.msg.queue;

import com.lovecyy.hello.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author ys
 * @topic
 * @date 2020/3/1 13:07
 */
@Service
public class MessageProducerService {

    @Autowired
    private RedisTemplate redisTemplate;

    public Long sendMeassage(User message) {
        System.out.println("发送了" + message);
        return redisTemplate.opsForList().leftPush("list", message);
    }
}
