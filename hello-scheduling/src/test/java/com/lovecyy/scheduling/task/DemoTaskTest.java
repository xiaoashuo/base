package com.lovecyy.scheduling.task;


import com.lovecyy.scheduling.pojo.SysJob;
import com.lovecyy.scheduling.service.SysJobService;
import com.lovecyy.scheduling.utils.SpringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author ys
 * @topic
 * @date 2020/3/3 14:32
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoTaskTest {

    private static Object object=new Object();
    @Autowired
    private SysJobService sysJobService;

    @Test
    public void taskWithParams() throws InterruptedException {
        SysJob sysJob = new SysJob();
        sysJob.setBeanName("demoTask");
        sysJob.setMethodName("taskWithParams");
        sysJob.setMethodParams("666");
        sysJob.setCronExpression("0/10 * * * * ?");
        sysJob.setJobStatus(1);
        sysJob.setRemark("ccc");
        boolean b = sysJobService.addJob(sysJob);
        Thread.sleep(30000);
        boolean b1 = sysJobService.delJob(sysJob);

        Thread.sleep(500000);
    }

    @Test
    public void taskWithParams1() {

    }
}
