package com.lovecyy.activiti;

import com.lovecyy.activiti.example01.HolidayDeployment;
import com.lovecyy.activiti.example01.RuntimeInstanceService;
import com.lovecyy.activiti.example01.TaskCompleteTask;
import com.lovecyy.activiti.example01.TaskQueryService;
import com.lovecyy.activiti.example04.AssigneeUEL;
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
public class AssigneeTest {


    @Autowired
    private AssigneeUEL assigneeUEL;

    @Test
    public void test() throws IOException {
      assigneeUEL.assigneeUel();

    }
}
