package com.lovecyy.datasource;

import com.lovecyy.samples.DataSourceApplication;

import com.lovecyy.samples.service.UserService;
import com.lovecyy.samples.config.DbProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ys
 * @topic
 * @date 2020/4/11 22:19
 */
@SpringBootTest(classes = DataSourceApplication.class)
@RunWith(SpringRunner.class)
public class DataSourceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private DbProperties dbProperties;

    @Autowired
private JdbcTemplate jdbcTemplate;
    @Test
    public void dataSourceTest(){
        String[] beanDefinitionNames = applicationContext.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName+"--"+applicationContext.getBean(beanDefinitionName));
        }

    }

    @Autowired
    private UserService userService;


    @Test
    public void testQuery(){
//        Integer integer = jdbcTemplate.queryForObject("select count(1) from `tb_order_item_0`", Integer.class);
////        System.out.println(integer);
     userService.getM();

    }
}
