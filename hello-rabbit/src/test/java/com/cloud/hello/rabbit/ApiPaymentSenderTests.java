package com.cloud.hello.rabbit;

import com.cloud.hello.rabbit.topic.ApiPaymentSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 21:46
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiPaymentSenderTests {

    @Autowired
    private ApiPaymentSender apiPaymentSender;

/*
    @Test
    public void testOrder(){
        apiPaymentSender.order("订单管理");
    }


    @Test
    public void testOrderQuery(){
        apiPaymentSender.order("查询订单信息");
    }


    @Test
    public void testOrderDetailQuery(){
        apiPaymentSender.order("查询订单详细");
    }
*/

    @Test
    public void test_Sender_many2one_1() throws Exception{
        for (int i=0;i<20;i+=2){
            apiPaymentSender.order("支付宝订单号"+i);
            Thread.sleep(1000);
        }
    }

    @Test
    public void test_Sender_many2one_2() throws Exception{
        for (int i=1;i<20;i+=2){
            apiPaymentSender.order("支付宝订单号"+i);
            Thread.sleep(1000);
        }
    }

    @Test
    public void test_sender_one2many(){
        for (int i=0;i<20;i++){
            apiPaymentSender.order("支付订单号"+i);
        }
    }
}
