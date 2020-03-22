package com.lovecyy.activiti;

import com.lovecyy.activiti.example01.HolidayDeployment;
import com.lovecyy.activiti.example01.RuntimeInstanceService;
import com.lovecyy.activiti.example01.TaskCompleteTask;
import com.lovecyy.activiti.example01.TaskQueryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * @author ys
 * @topic
 * @date 2020/3/18 10:19
 */
@SpringBootTest(classes = ActivitiApplication.class)
@RunWith(SpringRunner.class)
public class ActivitiTest {

   // @Autowired
 //   private ProcessEngineConfiguration configuration;
    @Test
    public void testGenTable(){
        ProcessEngineConfiguration configuration=ProcessEngineConfiguration.
                createProcessEngineConfigurationFromResource("activiti.cfg.xml");
        ProcessEngine processEngine = configuration.buildProcessEngine();
        System.out.println(processEngine);
    }
    @Autowired
    private ApplicationContext context;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ProcessEngineConfiguration configuration;

    @Autowired
    private HolidayDeployment holidayDeployment;
    @Autowired
    RuntimeInstanceService runtimeInstanceService;
    @Autowired
    TaskQueryService taskQuery;

    @Autowired
    private TaskCompleteTask taskCompleteTask;

    @Test
    public void test() throws IOException {
//        String[] beanDefinitionNames = context.getBeanDefinitionNames();
//        for (String beanDefinitionName : beanDefinitionNames) {
//            System.out.println(beanDefinitionName+"---"+context.getBean(beanDefinitionName));
//        }
//        int i = jdbcTemplate.queryForObject("select count(*) from `act_evt_log` ", Integer.class).intValue();
//        System.out.println(i);
        holidayDeployment.deploy();
        //runtimeInstanceService.runtime();
        //taskQuery.taskQuery();
      //  taskQuery.taskLisiQuery();
        //taskCompleteTask.completeLisi();
        taskCompleteTask.queryAndComplete();
       // holidayDeployment.zipDeploy();

    }
}
