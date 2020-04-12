package com.lovecyy.apache.shardingsphere.service.impl;

import com.lovecyy.apache.shardingsphere.ShardingSphereApplication;
import com.lovecyy.apache.shardingsphere.domain.TbOrder;
import com.lovecyy.apache.shardingsphere.domain.TbOrderItem;
import com.lovecyy.apache.shardingsphere.mapper.TbOrderItemMapper;
import com.lovecyy.apache.shardingsphere.mapper.TbOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import tk.mybatis.spring.annotation.MapperScan;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ys
 * @topic
 * @date 2020/4/11 20:50
 */
@SpringBootTest(classes = ShardingSphereApplication.class)
@RunWith(SpringRunner.class)
public class ShardingSphereTest {

    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderItemMapper tbOrderItemMapper;
    @Test
    public void context(){

        TbOrder tbOrder = new TbOrder();
        tbOrder.setOrderId(1L);
        tbOrder.setUserId(1L);
        tbOrderMapper.insert(tbOrder);
    }

    @Test
    public void testInsertOrderItem() {
        TbOrderItem tbOrderItem = new TbOrderItem();
        tbOrderItem.setUserId(2L);
        tbOrderItem.setOrderId(2L);
        tbOrderItem.setOrderItemId(2L);
        tbOrderItemMapper.insert(tbOrderItem);
    }
    @Test
    public void testSelectAll() {
        List<TbOrder> tbOrders = tbOrderMapper.selectAll();
        tbOrders.forEach(tbOrder -> {
            System.out.println(tbOrder);
        });
        List<TbOrderItem> tbOrderItems = tbOrderItemMapper.selectAll();
        tbOrderItems.forEach(tbOrderItem -> {
            System.out.println(tbOrderItem);
        });
    }

    @Autowired
    ApplicationContext applicationContext;

    @Test
    public void AllDataSources(){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName+"--"+applicationContext.getBean(beanDefinitionName));
        }
    }
}
