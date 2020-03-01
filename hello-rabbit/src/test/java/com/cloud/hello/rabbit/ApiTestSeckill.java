package com.cloud.hello.rabbit;

import com.cloud.hello.rabbit.seckill.SeckillNotifySender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;

/**
 * @author ys
 * @topic
 * @date 2020/3/1 10:13
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTestSeckill {

    @Autowired
    private SeckillNotifySender seckillNotifySender;
    CountDownLatch countDownLatch = new CountDownLatch(1);
    private int threadNum=1000;
    private volatile int count=0;
    @Test
    public void context(){

        for (int i=0;i<threadNum;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        countDownLatch.await();
                        count+=1;
                        seckillNotifySender.sender(count);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        countDownLatch.countDown();

    }
}
