package com.lovecyy.utils;

import com.lovecyy.config.MessageSourceHotLoading;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Locale;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageUtilsTest {



    @Autowired
    private MessageSourceHotLoading messageSourceHotLoading;


    @Test
    public void context(){
        messageSourceHotLoading.reloadBaseNames();
        String str="未找到群发任务信息,任务id:{0}";
        String message = MessageUtils.getMessage(str,new Object[]{3}, Locale.US);
        System.out.println(message);
    }
}