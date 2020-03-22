package com.lovecyy.activiti.example03;

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
 */
@Component
public class BusinessKeyService {

    @Autowired
    private ProcessEngineConfiguration configuration;

    public void runtime(){
        //1.获取ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //2.得到RunService对象
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //3.创建流程实列 参数1 流程定义的key 参数2: 并指定业务标识businessKey  它本身就是请假单的id
        ProcessInstance instance = runtimeService.startProcessInstanceByKey("holiday","123");
        //4.输出实列的相关信息
        System.out.println("流程业务ID"+instance.getBusinessKey());
    }
}
