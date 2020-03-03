package com.lovecyy.scheduling.config;

import com.lovecyy.scheduling.pojo.SysJob;
import com.lovecyy.scheduling.task.CronTaskRegistrar;
import com.lovecyy.scheduling.task.SchedulingRunnable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author ys
 * @topic
 * @date 2020/3/3 14:03
 */
@Component
public class SysJobRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(SysJobRunner.class);

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;
    @Override
    public void run(String... args) throws Exception {
        //初始化数据库理正常状态的定时任务
        List<SysJob> jobList=null;
        if (jobList!=null && jobList.size()>0){
            for (SysJob job : jobList) {
                SchedulingRunnable task = new SchedulingRunnable(job.getBeanName(), job.getMethodName(), job.getMethodParams());
                cronTaskRegistrar.addCronTask(task, job.getCronExpression());
            }
            logger.info("定时任务加载完毕。。。");
        }
    }
}
