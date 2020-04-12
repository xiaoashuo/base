package com.lovecyy.samples.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author ys
 * @topic
 * @date 2020/4/12 11:37
 */
@Service
public class UserService {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Transactional(transactionManager = "txManager2")
    public void getM(){
        jdbcTemplate.update("INSERT INTO `tb_order_0`(`id`,`order_id`,`user_id`) VALUES(5,10,11)");
        System.out.println("1");
    }
}
