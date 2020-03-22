package com.lovecyy.high.concurrency.example2;

import com.lovecyy.high.concurrency.HighConcurrencyApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author ys
 * @topic
 * @date 2020/3/21 15:50
 */
@SpringBootTest(classes = HighConcurrencyApplication.class)
@RunWith(SpringRunner.class)
public class SeckillMapperTest {

    @Autowired
    private SeckillMapper seckillMapper;

    @Autowired
    private SeckillService seckillService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void context(){
       /* Seckill iphoneX = seckillMapper.selectByGoodsName("iphoneX");
        System.out.println(iphoneX);*/
        Seckill seckill = new Seckill();
        seckill.setGoodsName("iphoneX");
        seckill.setGoodsNum(1);
       /* int i1 = seckillService.seckillGoodsVersion(seckill);
        System.out.println(i1);*/
    /*    int i = seckillMapper.updateGoodsVersion(seckill);
        System.out.println(i);*/
        Set shiro = redisTemplate.keys("shiro");
        System.out.println(shiro.size());
    }
}
