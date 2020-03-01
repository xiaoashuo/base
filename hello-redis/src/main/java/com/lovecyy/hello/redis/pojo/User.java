package com.lovecyy.hello.redis.pojo;

import java.io.Serializable;

/**
 * @author ys
 * @topic
 * @date 2020/3/1 12:44
 */
public class User implements Serializable {

    private Integer id;
    private String name;

    public User() {

    }

    public User(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
