package com.lovecyy.activiti.example01;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/**
 * 流程定义的部署
 * activiti表有哪些？
 *  act_re_deployment  部署信息
    act_re_procdef     流程定义的一些信息
    act_ge_bytearray   流程定义的bpmn文件及png文件
 */
@Component
public class HolidayDeployment {

    @Autowired
    private ProcessEngineConfiguration configuration;

    /**
     * 流程定义部署
     */
    public void deploy(){
        //1.创建ProcessEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //2.得到RepositoryService实列
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.进行部署
        Deployment deploy = repositoryService.createDeployment()
                .addClasspathResource("diagram/holiday1.bpmn")
                .addClasspathResource("diagram/holiday1.png")
                .name("请假申请流程")
                // org.activiti.bpmn.exceptions.XMLException: cvc-complex-type.2.4.a: 发现了以元素 'process' 开头的无效内容。
                .disableSchemaValidation()//禁用架构验证
                .deploy();
        //4.输出一些部署信息
        System.out.println(deploy.getId()+"---"+deploy.getName());
    }
    //流程定义部署  流程制作出来后要上传到服务器 zip文件更便于上传
    //ZIP部署
    public void zipDeploy() throws IOException {
        //1.创建ProcessEngine 对象
        ProcessEngine processEngine = configuration.buildProcessEngine();
        //将zip转为输入流
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("diagram/holiday.zip");
        ZipInputStream zipInputStream = new ZipInputStream(is);
        //2.得到RepositoryService实列
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //3.进行部署
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .name("请假申请流程")
                // org.activiti.bpmn.exceptions.XMLException: cvc-complex-type.2.4.a: 发现了以元素 'process' 开头的无效内容。
                .disableSchemaValidation()//禁用架构验证
                .deploy();
        //4.输出一些部署信息
        System.out.println(deploy.getId()+"---"+deploy.getName());
        zipInputStream.close();
        is.close();

    }
}
