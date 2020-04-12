package com.lovecyy.samples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ys
 * @topic
 * @date 2020/4/11 22:24
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class DataSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataSourceApplication.class,args);
    }
}
