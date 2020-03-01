package com.cloud.hello.rabbit.delay.retry;

import com.cloud.hello.rabbit.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ys
 * @topic
 * @date 2020/2/29 20:28
 */
@RestController
@RequestMapping("retry")
public class RetryController {

    @Autowired
    public RetrySender retrySender;

    @GetMapping("send")
    public String sendMessage(){
        User user = new User();
        user.setId(3);
        user.setName("张三0");
        user.setRetry(0);
        retrySender.send(user);
        return user.toString();
    }

    @GetMapping("send2")
    public String sendMessage2(){
        User user = new User();
        user.setId(1);
        user.setName("张三0");
        user.setRetry(0);
        retrySender.send(user);
        return user.toString();
    }
}
