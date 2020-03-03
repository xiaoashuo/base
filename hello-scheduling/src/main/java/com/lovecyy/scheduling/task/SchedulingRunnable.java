package com.lovecyy.scheduling.task;

import com.lovecyy.scheduling.utils.SpringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * @author ys
 * @topic
 * @date 2020/3/3 13:25
 */
public class SchedulingRunnable implements  Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SchedulingRunnable.class);

    private String beanName;
    private String methodName;
    private String params;

    public SchedulingRunnable(String beanName, String methodName, String params) {
        this.beanName = beanName;
        this.methodName = methodName;
        this.params = params;
    }
    public SchedulingRunnable(String beanName, String methodName) {
        this(beanName,methodName,null);
    }



    @Override
    public void run() {
        logger.info("定时任务开始执行-bean: {},方法: {},参数:{}",beanName,methodName,params);
        long startTime = System.currentTimeMillis();
        try {
            Object target = SpringUtils.getBean(beanName);
            Method method=null;
            if (StringUtils.isNotEmpty(params)){
                method=target.getClass().getDeclaredMethod(methodName,String.class);
            }else{
                method=target.getClass().getDeclaredMethod(methodName);
            }
            ReflectionUtils.makeAccessible(method);
            if (StringUtils.isNotEmpty(params)){
                method.invoke(target,params);
            }else{
                method.invoke(target);
            }
        } catch (Exception e) {
           logger.error("定时任务执行异常");
           e.printStackTrace();
        }
        long times =  System.currentTimeMillis()- startTime;
        logger.info("定时任务执行结束 - bean: {},方法:{},参数: {} ,耗时: {} 毫秒",
                beanName,methodName,params,times);

    }

    @Override
    public int hashCode() {
        if (params==null){
            return Objects.hash(beanName,methodName);
        }
        return Objects.hash(beanName,methodName,params);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SchedulingRunnable that = (SchedulingRunnable) o;
        if (params == null) {
            return beanName.equals(that.beanName) &&
                    methodName.equals(that.methodName) &&
                    that.params == null;
        }

        return beanName.equals(that.beanName) &&
                methodName.equals(that.methodName) &&
                params.equals(that.params);
    }
}
