package com.cloud.hello.rabbit;

import com.cloud.hello.rabbit.direct.PaymentNotifySender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HelloRabbitApplicationTests {

    @Autowired
    RabbitProvider rabbitProvider;

    @Test
    public void contextLoads() {
        for (int i=0;i<100;i++){
            rabbitProvider.send();
        }
    }

    @Autowired
    private PaymentNotifySender paymentNotifySender;

    @Test
    public void testDirect(){
        paymentNotifySender.sender("您好");
    }


}
