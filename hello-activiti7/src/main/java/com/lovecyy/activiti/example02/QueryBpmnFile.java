package com.lovecyy.activiti.example02;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author ys
 * @topic 需求:
 *    1.从act_ge_bytearray表中 读取两个资源文件
 *    2.将俩个资源文件保存到指定 路径下
 * 真实应用场景:用户想查看请假流程有哪些步骤要走？
 *技术方案:
 *   1.使用activiti的api实现
 *   2.其实就是原理层面 可以使用jdbc对blol clob类型数据的读取并保存
 *   3.io流转换 最好使用common-io 可以轻松解决
 * @date 2020/3/18 19:36
 */
@Component
public class QueryBpmnFile {

    @Autowired
    private ProcessEngineConfiguration configuration;

    /**
     * 得到资源文件到路径
     * @throws IOException
     */
    public void getResourceFileToPath() throws IOException {
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //2.得到RepositoryService对象
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.得到查询器 ProcessDefinitionQuery
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery();
        //4.设置查询条件    //流程定义的key
        processDefinitionQuery.processDefinitionKey("holiday");
        //5.执行查询操作 查询出想要的流程定义
        ProcessDefinition processDefinition = processDefinitionQuery.singleResult();
        //6.得到部署的id号
        String deploymentId = processDefinition.getDeploymentId();
        //7.通过repositoryService方法 实现读取图片信息和bpmn文件（输入流）
        //getResourceAsStream() 参数1: 部署id  参数2: 资源名称  getDiagramResourceName() 获取图片资源名称
        InputStream pngIs = repositoryService.getResourceAsStream(deploymentId, processDefinition.getDiagramResourceName());
        //getResourceName() 获取bpmn文件的名称
        InputStream bpmnIs = repositoryService.getResourceAsStream(deploymentId, processDefinition.getResourceName());

        //8.构建OutputStream

        OutputStream pngOs=new FileOutputStream("E:\\idea\\base\\hello-activiti7\\src\\main\\resources\\" + processDefinition.getDiagramResourceName());
        OutputStream bpmnOs=new FileOutputStream("E:\\idea\\base\\hello-activiti7\\src\\main\\resources\\"+processDefinition.getResourceName());
        //9.输入流输出流的转换
        IOUtils.copy(pngIs,pngOs);
        IOUtils.copy(bpmnIs,bpmnOs);
        //关闭流
        pngOs.close();
        bpmnOs.close();
        pngIs.close();
        bpmnIs.close();

    }



}
