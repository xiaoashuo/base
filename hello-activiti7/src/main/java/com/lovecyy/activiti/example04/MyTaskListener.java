package com.lovecyy.activiti.example04;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author ys
 * @topic 监听器
 * @date 2020/3/19 14:14
 */
public class MyTaskListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.setAssignee("张三");
    }
}
