package com.lovecyy.apache.shardingsphere;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author ys
 * @topic
 * @date 2020/4/11 0:17
 */
@SpringBootApplication
@MapperScan(basePackages = "com.lovecyy.apache.shardingsphere.mapper")
public class ShardingSphereApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereApplication.class,args);
    }
}
