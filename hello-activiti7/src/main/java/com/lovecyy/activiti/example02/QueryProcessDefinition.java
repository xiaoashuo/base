package com.lovecyy.activiti.example02;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ys
 * @topic 查询流程定义的查询
 * @date 2020/3/18 19:13
 */
@Component
public class QueryProcessDefinition {
    @Autowired
    private ProcessEngineConfiguration configuration;

    /**
     * 查询流程定义
     */
    public void processDefinition(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //得到流程定义查询对象
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        //设置条件 查询出所有流程定义 查询条件 流程定义的key=holiday
        List<ProcessDefinition> list = definitionQuery.processDefinitionKey("holiday")
                //设置排序方式
                .orderByProcessDefinitionVersion()
                .desc().list();
        //输出流程定义信息
        for (ProcessDefinition processDefinition : list) {
            System.out.println("流程定义id:"+processDefinition.getId());
            System.out.println("流程定义NAME:"+processDefinition.getName());
            System.out.println("流程定义KEY:"+processDefinition.getKey());
            System.out.println("流程定义VERSION:"+processDefinition.getVersion());
            System.out.println("流程部署ID:"+processDefinition.getDeploymentId());

        }

    }



}
