package com.cloud.hello.rabbit;

import com.cloud.hello.rabbit.pojo.ObjectSender;
import com.cloud.hello.rabbit.pojo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 23:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiObjectTest {

    @Autowired
    private ObjectSender objectSender;

    @Test
    public void testObject(){
        User user=new User();
        user.setId(1);
        user.setName("niah");
        objectSender.sender(user);
    }
    @Test
    public void testObjectRpc(){

        objectSender.senderRpc("xx");
    }
}
