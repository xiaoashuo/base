package com.lovecyy;

import lombok.AllArgsConstructor;
import lombok.Data;
@AllArgsConstructor
@Data
public class R {
    private Integer code;
    private String msg;
    private Object data;

    public static R ok(String msg){
        return createR(200,msg,null);
    }
    private static R createR(Integer code,String msg,Object data){
        return new R(code, msg, data);
    }

}
