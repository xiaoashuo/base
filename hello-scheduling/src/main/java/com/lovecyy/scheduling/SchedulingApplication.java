package com.lovecyy.scheduling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ys
 * @topic 动态增删改查定时任务 不依赖Quartz
 * @date 2020/3/3 13:14
 */
@SpringBootApplication
public class SchedulingApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchedulingApplication.class,args);
    }
}
