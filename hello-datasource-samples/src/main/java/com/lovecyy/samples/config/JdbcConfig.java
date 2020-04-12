package com.lovecyy.samples.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author ys
 * @topic
 * @date 2020/4/12 22:27
 */
@Configuration
public class JdbcConfig {
//    @Bean("masterJdbcTemplate")
//    @Primary
//    public JdbcTemplate masterJdbcTemplate(@Autowired @Qualifier("masterDataSource") DataSource masterDataSource){
//        return new JdbcTemplate(masterDataSource);
//    }
//    @Bean("slaveJdbcTemplate")
//    public JdbcTemplate slaveJdbcTemplate(@Autowired  @Qualifier("slaveDataSource") DataSource slaveDataSource){
//        return new JdbcTemplate(slaveDataSource);
//    }
}
