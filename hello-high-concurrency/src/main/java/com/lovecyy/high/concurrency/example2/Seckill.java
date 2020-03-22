package com.lovecyy.high.concurrency.example2;

import java.io.Serializable;

/**
 * @author ys
 * @topic
 * @date 2020/3/21 15:40
 */
public class Seckill implements Serializable {
    private Integer id;

    private String goodsName;

    private Integer goodsNum;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Integer goodsNum) {
        this.goodsNum = goodsNum;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "id=" + id +
                ", goodsName='" + goodsName + '\'' +
                ", goodsNum=" + goodsNum +
                ", version=" + version +
                '}';
    }
}
