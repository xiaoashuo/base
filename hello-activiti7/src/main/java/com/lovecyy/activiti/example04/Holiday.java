package com.lovecyy.activiti.example04;

import java.io.Serializable;
import java.util.Date;

/**
 * @author ys
 * @topic
 * @date 2020/3/19 15:07
 */
public class Holiday implements Serializable {

    private Integer id;
    private String holidayName;//请假人名字
    private Date beginDate;//开始时间
    private Date endData;//结束时间
    private Float num;//请假天数
    private String reason;//请假理由
    private String type;//请假类型

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHolidayName() {
        return holidayName;
    }

    public void setHolidayName(String holidayName) {
        this.holidayName = holidayName;
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndData() {
        return endData;
    }

    public void setEndData(Date endData) {
        this.endData = endData;
    }

    public Float getNum() {
        return num;
    }

    public void setNum(Float num) {
        this.num = num;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + id +
                ", holidayName='" + holidayName + '\'' +
                ", beginDate=" + beginDate +
                ", endData=" + endData +
                ", num=" + num +
                ", reason='" + reason + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
