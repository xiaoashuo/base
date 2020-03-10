package com.lovecyy.file.up.example1.service;

import com.lovecyy.file.up.example1.pojo.MultipartFileParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/3/10 14:18
 */
public interface UploadFileService {

    /**
     * 通过md5值查找文件对象
     * @param md5
     * @return
     */
    Map<String, Object> findByFileMd5(String md5);

    /**
     * 上传文件
     * @param form 文件表单信息
     * @param multipartFile 文件
     * @return
     */
    Map<String, Object> realUpload(MultipartFileParam form, MultipartFile multipartFile) throws IOException, Exception;
}
