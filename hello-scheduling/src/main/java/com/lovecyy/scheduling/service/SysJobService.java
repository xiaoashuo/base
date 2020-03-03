package com.lovecyy.scheduling.service;

import com.lovecyy.scheduling.enums.SysJobStatus;
import com.lovecyy.scheduling.pojo.SysJob;
import com.lovecyy.scheduling.task.CronTaskRegistrar;
import com.lovecyy.scheduling.task.SchedulingRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ys
 * @topic
 * @date 2020/3/3 14:09
 */
@Service
public class SysJobService {

    @Autowired
    private CronTaskRegistrar cronTaskRegistrar;

    public boolean addJob(SysJob sysJob){
        //插入数据库
        // insert
         int result=0;
        //判断是否成功
        if(result==0){
            //失败
            return false;
        }else{
            if (sysJob.getJobStatus().equals(SysJobStatus.NORMAL.ordinal())){
                SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
            }
            return true;
        }
    }

    public boolean edit(SysJob sysJob){
        //查询旧的数据 select
        SysJob existedSysJob=null;
        //数据库操作 update
        int result = 0;
            if (result==0) {
               return false;
            }else
            {
            //先移除再添加
            if (sysJob.getJobStatus().equals(SysJobStatus.NORMAL.ordinal())) {
                SchedulingRunnable task = new SchedulingRunnable(existedSysJob.getBeanName(), existedSysJob.getMethodName(), existedSysJob.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }

            if (sysJob.getJobStatus().equals(SysJobStatus.NORMAL.ordinal())) {
                SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
                cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
            }
            return  true;
        }

    }




    public boolean delJob(SysJob sysJob){
        //数据库删除操作
        int result=0;
        if (result==0){
            //失败
            return  false;
        }else{
            if (sysJob.getJobStatus().equals(SysJobStatus.NORMAL.ordinal())){
                SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
                cronTaskRegistrar.removeCronTask(task);
            }
            return true;
        }
    }

    public void upStatus(SysJob sysJob){
        if (sysJob.getJobStatus().equals(SysJobStatus.NORMAL.ordinal())) {
            SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
            cronTaskRegistrar.addCronTask(task, sysJob.getCronExpression());
        } else {
            SchedulingRunnable task = new SchedulingRunnable(sysJob.getBeanName(), sysJob.getMethodName(), sysJob.getMethodParams());
            cronTaskRegistrar.removeCronTask(task);
        }

    }
}
