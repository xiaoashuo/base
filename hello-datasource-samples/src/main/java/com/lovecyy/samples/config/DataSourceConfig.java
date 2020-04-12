package com.lovecyy.samples.config;

import com.zaxxer.hikari.HikariDataSource;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.ProxyTransactionManagementConfiguration;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.config.TxNamespaceHandler;
import org.springframework.transaction.interceptor.*;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/4/11 22:31
 */
//AOP代理自动配置，如果配@EnableAspectJAutoProxy表示使用cglib进行代理对象的生成；设置@EnableAspectJAutoProxy(exposeProxy=true)表示通过aop框架暴露该代理对象，aopContext能够访问.
@EnableAspectJAutoProxy
@Aspect
@Configuration
// implements TransactionManagementConfigurer
public class DataSourceConfig implements TransactionManagementConfigurer{
    @Autowired
    private DbProperties properties;

    @Bean(name = "masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        return new HikariDataSource(properties.getMaster());
    }
    @Bean(name = "txManager1")
    @Primary
    public PlatformTransactionManager txManager1() {
        return new DataSourceTransactionManager(masterDataSource());
    }

    @Bean(name = "slaveDataSource")
    @ConditionalOnProperty(prefix = "spring.datasource.slave", name = "enabled", havingValue = "true")
    public DataSource slaveDataSource() {
        return new HikariDataSource(properties.getSlave());
    }

    // 创建事务管理器2
    @Bean(name = "txManager2")
    @ConditionalOnProperty(prefix = "spring.datasource.slave", name = "enabled", havingValue = "true")
    public PlatformTransactionManager txManager2() {
        return new DataSourceTransactionManager(slaveDataSource());
    }
    @Autowired
    @Qualifier("txManager1")
    private PlatformTransactionManager txManager1;
    //碰到问题 springboot 配置DefaultPointcutAdvisor 和annotationDrivenTransactionManager
    //如果都使用txManager1() 就会报错 第二遍的txManager1为null  没有接收到赋予的值
    //TODO 未解决 不太理解 PlatformTransactionManager txManager1注入与txManager1() 区别
    // 在存在多个事务管理器的情况下，如果使用value具体指定
    // 则默认使用方法 annotationDrivenTransactionManager() 返回的事务管理器
    //可以在当前处设置断点  进去可以看到默认的事务管理器
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return txManager1;
     }

    private static final String AOP_POINTCUT_EXPRESSION = "execution(* com.lovecyy.samples..*.*(..))";


    @Bean
    public TransactionInterceptor txAdvice() {
       DefaultTransactionAttribute txAttr_REQUIRED = new DefaultTransactionAttribute();
       txAttr_REQUIRED.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

       DefaultTransactionAttribute txAttr_REQUIRED_READONLY = new DefaultTransactionAttribute();
       txAttr_REQUIRED_READONLY.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
       txAttr_REQUIRED_READONLY.setReadOnly(true);

       NameMatchTransactionAttributeSource source = new NameMatchTransactionAttributeSource();

       source.addTransactionalMethod("save*", txAttr_REQUIRED);
       source.addTransactionalMethod("add*", txAttr_REQUIRED);
       source.addTransactionalMethod("create*", txAttr_REQUIRED);
       source.addTransactionalMethod("update*", txAttr_REQUIRED);
       source.addTransactionalMethod("delete*", txAttr_REQUIRED);
       source.addTransactionalMethod("exec*", txAttr_REQUIRED);
       source.addTransactionalMethod("set*", txAttr_REQUIRED);
       source.addTransactionalMethod("get*", txAttr_REQUIRED_READONLY);
       source.addTransactionalMethod("query*", txAttr_REQUIRED_READONLY);
       source.addTransactionalMethod("find*", txAttr_REQUIRED_READONLY);
       source.addTransactionalMethod("list*", txAttr_REQUIRED_READONLY);
       source.addTransactionalMethod("count*", txAttr_REQUIRED_READONLY);
       source.addTransactionalMethod("is*", txAttr_REQUIRED_READONLY);

       return new TransactionInterceptor(txManager1, source);
     }

    @Bean
    public Advisor txAdviceAdvisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_POINTCUT_EXPRESSION);
        return new DefaultPointcutAdvisor(pointcut, txAdvice());
    }


}
