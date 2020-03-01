package com.cloud.hello.rabbit;

import com.cloud.hello.rabbit.topic.ApiCoreSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 21:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiCoreSenderTests {
    @Autowired
    private ApiCoreSender apiCoreSender;

    @Test
    public void testUser(){
        apiCoreSender.user("用户管理");
    }
    @Test
    public void testUserQuery(){
        apiCoreSender.userQuery("查询用户信息");
    }


}
