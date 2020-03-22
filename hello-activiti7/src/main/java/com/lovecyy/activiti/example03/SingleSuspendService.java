package com.lovecyy.activiti.example03;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *  单个流程实列的挂起激活
 *
 */
@Component
public class SingleSuspendService {

    @Autowired
    private ProcessEngineConfiguration configuration;

    public void runtime(){
        //1.获取ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //查询流程实列
        ProcessInstanceQuery processInstanceQuery = runtimeService.createProcessInstanceQuery();
        ProcessInstance processInstance = processInstanceQuery.processDefinitionKey("holiday")
                .singleResult();
        // 得到流程定义是否暂停
        boolean suspended = processInstance.isSuspended();
        String processInstanceId = processInstance.getId();
        if (suspended){
            //执行激活
            runtimeService.activateProcessInstanceById(processInstanceId);
            System.out.println("流程定义:"+processInstanceId+"被激活");
        }else{
            //执行暂停
            runtimeService.suspendProcessInstanceById(processInstanceId);
            System.out.println("流程定义:"+processInstanceId+"被挂起");
        }

    }
}
