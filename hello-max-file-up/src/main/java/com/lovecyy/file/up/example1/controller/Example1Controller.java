package com.lovecyy.file.up.example1.controller;


import com.lovecyy.file.up.example1.pojo.MultipartFileParam;
import com.lovecyy.file.up.example1.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/3/8 17:35
 */
@Controller
@RequestMapping("example1")
public class Example1Controller {

    @Autowired
    private UploadFileService uploadFileService;

    @PostMapping("upload")
    @ResponseBody
    public Map<String, Object> upload(MultipartFileParam form,
                                      @RequestParam(value = "data", required = false)MultipartFile multipartFile ) throws IOException {
        Map<String, Object> map = null;

        try {
            map = uploadFileService.realUpload(form, multipartFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


   @PostMapping("/isUpload")
   @ResponseBody
   public Map<String,Object> isUpload(MultipartFileParam param){
      return uploadFileService.findByFileMd5(param.getMd5());
   }






}
