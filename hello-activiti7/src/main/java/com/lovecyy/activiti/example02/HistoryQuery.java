package com.lovecyy.activiti.example02;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
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
public class HistoryQuery {
    @Autowired
    private ProcessEngineConfiguration configuration;

    /**
     * 查询流程定义
     */
    public void historyQuery(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //得到historyService
        HistoryService historyService = processEngine.getHistoryService();
        //得到HistoricActivityInstanceQuery
        HistoricActivityInstanceQuery historicActivityInstanceQuery = historyService.createHistoricActivityInstanceQuery();
        //设置流程实列id
        historicActivityInstanceQuery.processInstanceId("5001");
        historicActivityInstanceQuery.orderByHistoricActivityInstanceStartTime().asc();
        List<HistoricActivityInstance> list = historicActivityInstanceQuery.list();
        //遍历查询结果
        for (HistoricActivityInstance historicActivityInstance : list) {
            System.out.println(historicActivityInstance.getActivityId());
            System.out.println(historicActivityInstance.getActivityName());
            System.out.println(historicActivityInstance.getProcessDefinitionId());
            System.out.println(historicActivityInstance.getProcessInstanceId());
            System.out.println("=======================");
        }


    }



}
