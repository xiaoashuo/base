package com.lovecyy.scheduling.task;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.config.CronTask;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ys
 * @topic 定时任务注册类 用来增加删除定时任务
 * @date 2020/3/3 13:47
 */
@Component
public class CronTaskRegistrar implements DisposableBean {
    private final Map<Runnable, ScheduledTask> scheduledTasks = new ConcurrentHashMap<>(16);

    @Autowired
    private TaskScheduler taskScheduler;

    public TaskScheduler getTaskScheduler() {
        return taskScheduler;
    }

    public void addCronTask(Runnable task,String cronExpression){
        addCronTask(new CronTask(task,cronExpression));
    }

    public void addCronTask(CronTask cronTask){
        if (cronTask!=null){
            Runnable task = cronTask.getRunnable();
            if (this.scheduledTasks.containsKey(task)){
                removeCronTask(task);
            }
            this.scheduledTasks.put(task,scheduleCronTask(cronTask));
        }
    }

    public ScheduledTask scheduleCronTask(CronTask cronTask) {
        ScheduledTask scheduledTask = new ScheduledTask();
        scheduledTask.future=this.taskScheduler.schedule(cronTask.getRunnable(),cronTask.getTrigger());
        return scheduledTask;
    }

    public void removeCronTask(Runnable task) {
        ScheduledTask scheduledTask = this.scheduledTasks.remove(task);
        if (scheduledTask!=null){
            scheduledTask.cancel();
        }
    }

    @Override
    public void destroy() throws Exception {
     scheduledTasks.values().forEach(task->{
         task.cancel();
     });
     this.scheduledTasks.clear();
    }
}
