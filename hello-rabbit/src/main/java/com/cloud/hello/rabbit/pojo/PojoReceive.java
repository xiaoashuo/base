package com.cloud.hello.rabbit.pojo;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 23:02
 */
@Component
public class PojoReceive {
    @RabbitHandler
    @RabbitListener(queues = "api.object")
    public void receive(User user){
        System.out.println("receive"+user);
    }
    @RabbitHandler
    @RabbitListener(queues = "api.object.receive")
    public User receiveObject(String name){
        System.out.println("receive"+name);
        User user=new User();
        user.setId(1);
        user.setName("张三");
        return user;
    }
}
