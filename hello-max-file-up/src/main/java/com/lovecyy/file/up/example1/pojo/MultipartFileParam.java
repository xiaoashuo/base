package com.lovecyy.file.up.example1.pojo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @author ys
 * @topic
 * @date 2020/3/8 13:01
 */
@Data
public class MultipartFileParam implements Serializable {
    private String md5;

    private String uuid;

    private String date;

    private String name;

    private String size;

    private String total;

    private String index;

    private String action;

    private String partMd5;



}
