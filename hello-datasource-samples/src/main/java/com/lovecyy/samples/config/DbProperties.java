package com.lovecyy.samples.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/4/11 22:46
 */
@Component
@ConfigurationProperties("spring.datasource")
public class DbProperties {
    private  HikariConfig master;
    private  HikariConfig slave;

    public HikariConfig getMaster() {
        return master;
    }

    public void setMaster(HikariConfig master) {
        this.master = master;
    }

    public HikariConfig getSlave() {
        return slave;
    }

    public void setSlave(HikariConfig slave) {
        this.slave = slave;
    }
}
