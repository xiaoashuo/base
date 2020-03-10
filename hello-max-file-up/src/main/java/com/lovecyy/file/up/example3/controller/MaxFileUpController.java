package com.lovecyy.file.up.example3.controller;

import com.lovecyy.file.up.example3.pojo.MultipartFileParam;
import com.lovecyy.file.up.example3.utils.Constants;
import com.lovecyy.file.up.example3.utils.FileUploadUtils;
import com.lovecyy.file.up.example3.vo.ResultStatus;
import com.lovecyy.file.up.example3.vo.ResultVo;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @author ys
 * @topic
 * @date 2020/3/8 13:03
 */
@Controller
@RequestMapping("api")
public class MaxFileUpController {

    private static final Logger log= LoggerFactory.getLogger(MaxFileUpController.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private FileUploadUtils fileUploadUtils;






    @PostMapping("checkFileMd5")
    @ResponseBody
    public Object checkFileMd5(String md5) throws IOException{
        //根据md5查询当前文件处理状态
        Object processingObj = stringRedisTemplate.opsForHash().get(Constants.FILE_UPLOAD_STATUS, md5);
        //若未查询到对象 则返回错误提示 文件未上传过
        if (processingObj==null){
            return new ResultVo(ResultStatus.NO_HAVE);
        }
        String processingStr = processingObj.toString();
        //当前文件上传状态
        boolean processing = Boolean.parseBoolean(processingStr);
        String value = stringRedisTemplate.opsForValue().get(Constants.FILE_MD5_KEY + md5);
        if (processing){
            return new ResultVo(ResultStatus.IS_HAVE,value);
        }else{
            File confFile = new File(value);
            byte[] completeList = FileUtils.readFileToByteArray(confFile);
            LinkedList<String> missChunkList = new LinkedList<>();
            for (int i = 0; i < completeList.length; i++) {
                if (completeList[i]!=Byte.MAX_VALUE){
                    missChunkList.add(i+"");
                }
            }
            return new ResultVo<>(ResultStatus.ING_HAVE,missChunkList);
        }


    }

    @PostMapping("fileUpload")
    @ResponseBody
    public ResponseEntity fileUpload(MultipartFileParam param,HttpServletRequest request){
        boolean multipartContent = ServletFileUpload.isMultipartContent(request);
        if (multipartContent){
            log.info("上传文件start");
            // 方法1
            //storageService.uploadFileRandomAccessFile(param);
            // 方法2 这个更快点
            try {
                fileUploadUtils.uploadFileByMappedByteBuffer(param);
            } catch (IOException e) {
                e.printStackTrace();
                log.error("上传文件错误");
            }
            log.info("上传文件结束");
        }
         return  ResponseEntity.ok().body("上传成功");
    }
}
