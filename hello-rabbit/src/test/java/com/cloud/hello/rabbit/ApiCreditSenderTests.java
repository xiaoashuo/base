package com.cloud.hello.rabbit;

import com.cloud.hello.rabbit.header.ApiCreditSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 22:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiCreditSenderTests {

    @Autowired
    private ApiCreditSender sender;

    @Test
    public void testCreditBank(){
        Map<String,Object> head=new HashMap<>();
        head.put("type","cash");
        sender.creditBank(head,"银行授信(部分匹配)");
    }

    @Test
    public void testCreditBankAll(){
        Map<String,Object> head=new HashMap<>();
        head.put("type","cash");
        head.put("aping","fast");
        sender.creditBank(head,"银行授信(全部匹配)");
    }

    @Test
    public void testCreditFinanace(){
        Map<String,Object> head=new HashMap<>();
        head.put("type","cash");
        sender.creditFinance(head,"金融公司授信(部分匹配)");
    }
    @Test
    public void testCreditFinanceAll(){
        Map<String,Object> head=new HashMap<>();
        head.put("type","cash");
        head.put("aping","fast");
        sender.creditFinance(head,"金融公司授信(全部匹配)");
    }
}
