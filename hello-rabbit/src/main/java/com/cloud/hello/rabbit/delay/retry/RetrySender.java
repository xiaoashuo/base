package com.cloud.hello.rabbit.delay.retry;

import com.cloud.hello.rabbit.pojo.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 20:12
 */
@Component
public class RetrySender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(User user){
        rabbitTemplate.convertAndSend(WorkConfig.WORK_EXCHANGE,
                WorkConfig.WORK_KEY,user);
    }
}
