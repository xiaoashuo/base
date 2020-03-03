package com.lovecyy.scheduling.task;

import org.springframework.stereotype.Component;

/**
 * @author ys
 *@topic
 * @date 2020/3/3 13:57
 */
@Component("demoTask")
public class DemoTask {

    public void  taskWithParams(){
        System.out.println("执行无参任务");
    }
    public void  taskWithParams(String params){
        System.out.println("执行有参任务"+params);
    }

}
