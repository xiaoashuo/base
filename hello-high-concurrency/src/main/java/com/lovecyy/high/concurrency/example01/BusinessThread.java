package com.lovecyy.high.concurrency.example01;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author ys
 * @topic
 * @date 2020/3/21 10:45
 */
@Component
@Scope("prototype")
public class BusinessThread implements Runnable {

    private String acceptStr;

    public BusinessThread(String acceptStr) {
        this.acceptStr = acceptStr;
    }

    public String getAcceptStr() {
        return acceptStr;
    }

    public void setAcceptStr(String acceptStr) {
        this.acceptStr = acceptStr;
    }

    @Override
    public void run() {
        System.out.println("多线程已经处理订单插入系统,订单号:"+acceptStr);
    }
}
