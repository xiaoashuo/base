package com.lovecyy.scheduling.controller;

import com.lovecyy.scheduling.pojo.SysJob;
import com.lovecyy.scheduling.service.SysJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ys
 * @topic
 * @date 2020/3/3 14:42
 */
@RestController
@RequestMapping("job")
public class TaskController {
    @Autowired
    private SysJobService sysJobService;
    @GetMapping("add")
    public String tt(){
        SysJob sysJob = new SysJob();
        sysJob.setBeanName("demoTask");
        sysJob.setMethodName("taskWithParams");
        sysJob.setMethodParams("666");
        sysJob.setCronExpression("0/10 * * * * ?");
        sysJob.setJobStatus(1);
        sysJob.setRemark("ccc");
        boolean b = sysJobService.addJob(sysJob);
        return b+"";
    }
}
