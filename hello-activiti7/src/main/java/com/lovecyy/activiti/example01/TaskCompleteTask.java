package com.lovecyy.activiti.example01;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 处理当前用户的任务
 * 背后操作的表：
 *   act_hi_actinst
     act_hi_identitylink
     act_hi_taskinst
     act_ru_identitylink
     act_ru_task
 */
@Component
public class TaskCompleteTask {

    @Autowired
    private ProcessEngineConfiguration configuration;
    //张三完成自己的任务
    public void complete(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //处理任务 结合当前用户列表的查询 完成 任务id 5005
        taskService.complete("5005");
    }
    //李四完成自己的任务
    public void completeLisi(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //处理任务 结合当前用户列表的查询 完成 任务id 5005
        taskService.complete("7502");
    }
    //查询并完成
    public void queryAndComplete(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task holiday = taskService.createTaskQuery()
                .processDefinitionKey("holiday1")
                .taskAssignee("zhangsan")
                .singleResult();
        //处理任务 结合当前用户列表的查询 完成 任务id 5005
        taskService.complete(holiday.getId());
        System.out.println(holiday.getId());
    }


}
