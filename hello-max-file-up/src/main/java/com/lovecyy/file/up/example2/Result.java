package com.lovecyy.file.up.example2;



import com.alibaba.fastjson.JSON;

/**
 * Created by Beibei on 19/02/22
 * API响应结果
 */
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result setCode(Integer code) {
        this.code = code;
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static <T>  Result<T> fail(Integer code,T data) {
        Result<T> ret = new Result<T>();
        ret.setCode(code);
        ret.setData(data);
        return ret;
    }

    public static <T>  Result<T> failMessage(Integer code,String msg) {
        Result<T> ret = new Result<T>();
        ret.setCode(code);
        ret.setMessage(msg);
        return ret;
    }
    public static <T>  Result<T> successMessage(Integer code,String msg) {
        Result<T> ret = new Result<T>();
        ret.setCode(code);
        ret.setMessage(msg);
        return ret;
    }

    public static <T> Result<T> success(Integer code,T data) {
        Result<T> ret = new Result<T>();
        ret.setCode(code);
        ret.setData(data);
        return ret;
    }

}
