package com.lovecyy.activiti;

import com.lovecyy.activiti.example05.GroupService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ys
 * @topic
 * @date 2020/3/19 18:51
 */
@SpringBootTest(classes = ActivitiApplication.class)
@RunWith(SpringRunner.class)
public class GroupTest {

    @Autowired
    private GroupService groupService;

    @Test
    public void findGroup(){
        groupService.candidateQueryTask();
    }
}
