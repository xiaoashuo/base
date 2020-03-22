package com.lovecyy.activiti.example01;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 启动流程实例:
 *     前提是先已经完成流程定义的部署工作
 *
 *     背后影响的表：
 *       act_hi_actinst     已完成的活动信息
         act_hi_identitylink   参与者信息
         act_hi_procinst   流程实例
         act_hi_taskinst   任务实例
         act_ru_execution   执行表
         act_ru_identitylink   参与者信息
         act_ru_task  任务
 */
@Component
public class RuntimeInstanceService {

    @Autowired
    private ProcessEngineConfiguration configuration;

    public void runtime(){
        //1.获取ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //2.得到RunService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3.创建流程实列
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holiday");
        //4.输出实列的相关信息
        System.out.println("流程活动ID"+instance.getActivityId());
        System.out.println("流程部署ID"+instance.getDeploymentId());
        System.out.println("流程实列ID"+instance.getId());
        System.out.println("流程定义ID"+instance.getProcessDefinitionId());
    }
}
