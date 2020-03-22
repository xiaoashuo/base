package com.lovecyy.activiti.example04;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ys
 * @topic 流程变量的测试
 * @date 2020/3/19 15:24
 */
@Component
public class VariableService {

    @Autowired
    private ProcessEngineConfiguration configuration;
    //新的请假流程定义的部署

    public void deploy(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .disableSchemaValidation()
                .addClasspathResource("diagram/holiday4.bpmn")
                .addClasspathResource("diagram/holiday4.png")
                .name("请假流程-流程变量")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }
    //启动流程实列 还要设置流程变量的值
    public void start(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        Map<String,Object> map=new HashMap<>();
        Holiday holiday = new Holiday();
        holiday.setNum(1F);
        map.put("holiday",holiday);
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday4",map);
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getProcessDefinitionId());
        System.out.println(processInstance.getName());
    }

    //完成任务 zhangsan  lisi  wangwu  判断流程变量的请假天数--1天 人事经理存档
    public void complete(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //查询当前是否有任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey("holiday4")
                .taskAssignee("zhaoliu").singleResult();
        //如果task!=null 说明当前用户有任务
        if (task!=null){
            taskService.complete(task.getId());
            System.out.println("任务执行完毕");
        }
    }
}
