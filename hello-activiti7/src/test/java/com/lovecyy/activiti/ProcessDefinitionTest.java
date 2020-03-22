package com.lovecyy.activiti;

import com.lovecyy.activiti.example02.DeleteProcessDefinition;
import com.lovecyy.activiti.example02.HistoryQuery;
import com.lovecyy.activiti.example02.QueryBpmnFile;
import com.lovecyy.activiti.example02.QueryProcessDefinition;
import com.lovecyy.activiti.example03.BusinessKeyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

/**
 * @author ys
 * @topic
 * @date 2020/3/18 19:19
 */
@SpringBootTest(classes = ActivitiApplication.class)
@RunWith(SpringRunner.class)
public class ProcessDefinitionTest {

    @Autowired
    private QueryProcessDefinition queryProcessDefinition;
    @Autowired
    private DeleteProcessDefinition deleteProcessDefinition;

    @Test
    public void testQuery(){
        queryProcessDefinition.processDefinition();
    }

    @Test
    public void testDelete(){
        deleteProcessDefinition.deleteProcessDefinition();
    }

    @Autowired
    private QueryBpmnFile queryBpmnFile;

    @Test
    public void testGetResource() throws IOException {
      queryBpmnFile.getResourceFileToPath();

    }

    @Autowired
    private HistoryQuery historyQuery;
    @Test
    public void testQueryHistory() throws IOException {
        historyQuery.historyQuery();

    }
    @Autowired
    BusinessKeyService businessKeyService;
    @Test
    public void testBusinessKey() throws IOException {
        businessKeyService.runtime();

    }
}
