package com.cloud.hello.rabbit.pojo;

import java.io.Serializable;

/**
 * @author ys
 * @topic
 * @date 2020/2/28 23:00
 */
public class User implements Serializable {
    private int id;
    private String name;

    private Integer retry;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getRetry() {
        return retry;
    }

    public void setRetry(Integer retry) {
        this.retry = retry;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", retry=" + retry +
                '}';
    }
}
