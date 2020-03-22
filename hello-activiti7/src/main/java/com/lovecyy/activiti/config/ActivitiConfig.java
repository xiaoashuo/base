package com.lovecyy.activiti.config;

import com.zaxxer.hikari.HikariDataSource;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author ys
 * @topic
 * @date 2020/3/18 10:14
 */
@Configuration
public class ActivitiConfig {



    @Bean
    public ProcessEngineConfiguration processEngineConfiguration(DataSource dataSource){
        StandaloneProcessEngineConfiguration spec = new StandaloneProcessEngineConfiguration();
        spec.setDataSource(dataSource);
      //  spec.setDatabaseSchemaUpdate("true");
        return spec;
    }
}
