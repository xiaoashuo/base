package com.lovecyy.activiti.example05;

import com.lovecyy.activiti.example04.Holiday;
import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/3/19 18:44
 */
@Component
public class GroupService {

    @Autowired
    private ProcessEngineConfiguration configuration;
    //新的请假流程定义的部署

    public void deploy(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment()
                .disableSchemaValidation()
                .addClasspathResource("diagram/holiday5.bpmn")
              //  .addClasspathResource("diagram/holiday4.png")
                .name("请假流程-流程变量")
                .deploy();
        System.out.println(deploy.getId());
        System.out.println(deploy.getName());
    }
    //查询候选用户的组任务
    public void candidateQueryTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String key="holiday5";
        String cadidate_user="zhangsan";
        List<Task> list = taskService.createTaskQuery().processDefinitionKey(key)
                .taskCandidateUser(cadidate_user) //设置候选人
                .list();
        for (Task task : list) {
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee()); //当前张三只是候选人 不是执行人
        }
    }

    //测试拾取组任务  拾取任务 就是将候选用户 转化为真正任务的负责人 让任务的assign有值
    public void claimTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String key="holiday5";
        String cadidate_user="zhangsan";
        //查询当前是否有任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskCandidateUser(cadidate_user).singleResult();
        //如果task!=null 说明当前用户有任务
        if (task!=null){
            taskService.claim(task.getId(),cadidate_user);//1.任务id 2. 候选具体人
            System.out.println("任务拾取完毕");
        }
    }

    public void unClaimTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String key="holiday5";
        String cadidate_user="zhangsan";
        //查询当前是否有任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(cadidate_user).singleResult();

        if (task!=null){
            //如果设置为null 归还组任务 。该任务没负责人
            //也可以指定 委托给其他人 不是候选组的也可以  不过不建议这样做
            taskService.setAssignee(task.getId(),null);
            System.out.println("任务归还完毕");
        }
    }
    //任务交接 前提要保证当前用户 是这个任务的负责人 这时候他才有权限去将
    //任务委托给其他人
    public void delegateTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String key="holiday5";
        String cadidate_user="zhangsan";
        //查询当前是否有任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(cadidate_user).singleResult();

        if (task!=null){
            //1.当前任务id  2. 交接任务给lisi 交接任务 就是一个候选人拾取任务的过程
            taskService.setAssignee(task.getId(),"lisi");
            System.out.println("交接任务完成");
        }
    }
    //用户查询自己的任务
    public void queryTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String key="holiday5";
        String cadidate_user="zhangsan";
        //查询当前是否有任务
        List<Task> list = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(cadidate_user).list();

        for (Task task : list) {
            System.out.println(task.getProcessInstanceId());
            System.out.println(task.getId());
            System.out.println(task.getName());
            System.out.println(task.getAssignee()); //当前张三只是候选人 不是执行人
        }
    }
    //用户完成自己的任务
    public void completeTask(){
        ProcessEngine processEngine = configuration.buildProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        String key="holiday5";
        String cadidate_user="zhangsan";
        //查询当前是否有任务
        Task task = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskAssignee(cadidate_user).singleResult();

        if (task!=null){
            taskService.complete(task.getId());
            System.out.println("任务执行完毕");
        }
    }
}
