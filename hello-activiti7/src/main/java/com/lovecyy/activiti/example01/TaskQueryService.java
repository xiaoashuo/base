package com.lovecyy.activiti.example01;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ys
 * @topic
 * @date 2020/3/18 13:08
 */
@Component
public class TaskQueryService {


    @Autowired
    private ProcessEngineConfiguration configuration;

   //张三自己任务列表的查询
    public void taskQuery(){
        String assign="zhangsan";
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        List<Task> holidays = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee(assign)
                .list();
        for (Task holiday : holidays) {
            System.out.println("流程实列ID"+holiday.getProcessInstanceId());
            System.out.println("任务ID"+holiday.getId());
            System.out.println("任务负责人ID"+holiday.getAssignee());
            System.out.println("任务名称"+holiday.getName());
        }
    }
    //李四任务列表的查询
    public void taskLisiQuery(){
        String assign="lisi";
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        Task holiday = taskService.createTaskQuery()
                .processDefinitionKey("holiday")
                .taskAssignee(assign)
                .singleResult();

            System.out.println("流程实列ID"+holiday.getProcessInstanceId());
            System.out.println("任务ID"+holiday.getId());
            System.out.println("任务负责人ID"+holiday.getAssignee());
            System.out.println("任务名称"+holiday.getName());

    }
}
