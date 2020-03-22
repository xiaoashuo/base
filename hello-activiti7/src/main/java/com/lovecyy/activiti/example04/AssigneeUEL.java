package com.lovecyy.activiti.example04;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ys
 * @topic 启动流程实列动态设置assignee
 * @date 2020/3/19 13:06
 */
@Component
public class AssigneeUEL {

    @Autowired
    private ProcessEngineConfiguration configuration;

    public void assigneeUel(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RuntimeService runtimeService = processEngine.getRuntimeService();
        //设置assignee的取值 用户可以自己选取
        Map<String,Object> map=new HashMap<>();
        map.put("assignee01","zhangsan");
        map.put("assignee02","lisi");
        map.put("assignee03","wangwu");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holiday1", map);
        //输出
        System.out.println(processInstance.getName());

    }
}
