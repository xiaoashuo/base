package com.cloud.hello.rabbit.pojo;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 23:03
 */
@Component
public class ObjectSender {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sender(User user){
        System.out.println("send"+user);
        amqpTemplate.convertAndSend("api.object",user);
    }

    public void senderRpc(String name){
        System.out.println("send"+name);
        User o = (User) amqpTemplate.convertSendAndReceive("api.object.receive", name);
        System.out.println("得到rpc同步调用返回结果");
    }
}
