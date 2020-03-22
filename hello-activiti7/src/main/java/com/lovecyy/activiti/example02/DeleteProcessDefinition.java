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
 * @topic 删除流程定义的查询
 *   背后影响的表也会删除
 * 注意事项:
 *    1.当我们正在执行的这一套流程么有完全审批结束的时候
 *      此时如果要删除流程定义信息 就会失败
 *    2.如果公司层面要强制删除,可以用
 *    repositoryService.deleteDeployment("15001",true);
 *    参数true代表级联删除 此时就会先删除没有完成的流程节点,最后就可以删除流程定义信息  false 代表不级联 默认就是false
 * @date 2020/3/18 19:13
 */
@Component
public class DeleteProcessDefinition {
    @Autowired
    private ProcessEngineConfiguration configuration;



    public void deleteProcessDefinition(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //执行删除流程定义 参数代表流程部署ID
        repositoryService.deleteDeployment("15001");
    }

}
