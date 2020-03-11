package com.lovecyy.file.up.example1.controller;


import com.lovecyy.file.up.example1.pojo.MultipartFileParam;
import com.lovecyy.file.up.example1.service.UploadFileService;
import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/3/8 17:35
 */
@Controller
@RequestMapping("example1")
public class Example1Controller {

    private Logger log = LoggerFactory.getLogger(Example1Controller.class);

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

   @GetMapping("download/{fileName}")
   @ResponseBody
   public void videoStart(@PathVariable String fileName, HttpServletRequest request, HttpServletResponse response){
      String  filePath="E:\\idea\\base\\file\\1583847208056206066\\";
      String realPath = filePath + fileName;
      chunkDown(realPath,request,response);
    }

    /*******************************************************/
    /**
     * 若要实现文件限速下载 只需要后台向前台返回流的时候 睡眠一下就好
     */
    /*******************************************************/
    /**
     * 文件分片下载
     * @param filePath
     * @param request
     * @param response
     */
   public void  chunkDown(String filePath, HttpServletRequest request, HttpServletResponse response){

       String range = request.getHeader("Range");
       log.info("current request rang:" + range);
       File file = new File(filePath);
       //开始下载位置
       long startByte = 0;
       //结束下载位置
       long endByte = file.length() - 1;
       log.info("文件开始位置：{}，文件结束位置：{}，文件总长度：{}", startByte, endByte, file.length());
       if (range!=null && range.contains("bytes=")&&range.contains("-")){
           range = range.substring(range.lastIndexOf("=") + 1).trim();
           String[] ranges = range.split("-");
           try{
               //判断range的类型
               if (ranges.length == 1) {
                   //类型一：bytes=-2343
                   if (range.startsWith("-")) {
                       endByte = Long.parseLong(ranges[0]);
                   }
                   //类型二：bytes=2343-
                   else if (range.endsWith("-")) {
                       startByte = Long.parseLong(ranges[0]);
                   }
               } else if (ranges.length == 2) {
                   //类型三：bytes=22-2343
                   startByte = Long.parseLong(ranges[0]);
                   endByte = Long.parseLong(ranges[1]);
               }
           }catch (NumberFormatException e){
               startByte=0;
               endByte=file.length()-1;
               log.error("Range Occur Error, Message:{}",e.getLocalizedMessage());
           }
           //要下载的长度
           long contentLength = endByte - startByte + 1;
           //文件名
           String fileName = file.getName();
           //文件类型
           String contentType = request.getServletContext().getMimeType(fileName);
           ////解决下载文件时文件名乱码问题
           byte[] fileNameBytes = fileName.getBytes(StandardCharsets.UTF_8);
           fileName = new String(fileNameBytes, 0, fileNameBytes.length, StandardCharsets.ISO_8859_1);
          //各种响应头设置
           //支持断点续传，获取部分字节内容：
           response.setHeader("Accept-Ranges", "bytes");
           //http状态码要为206：表示获取部分内容
           response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
           response.setContentType(contentType);
           response.setHeader("Content-Type", contentType);
           //inline表示浏览器直接使用，attachment表示下载，fileName表示下载的文件名
           response.setHeader("Content-Disposition", "inline;filename=" + fileName);
           response.setHeader("Content-Length", String.valueOf(contentLength));
           // Content-Range，格式为：[要下载的开始位置]-[结束位置]/[文件总大小]
           response.setHeader("Content-Range", "bytes " + startByte + "-" + endByte + "/" + file.length());

           BufferedOutputStream outputStream = null;
           RandomAccessFile randomAccessFile = null;
           //已传送数据大小
           long transmitted = 0;
           try {
               randomAccessFile = new RandomAccessFile(file, "r");
               outputStream = new BufferedOutputStream(response.getOutputStream());
               byte[] buff = new byte[4096];
               int len = 0;
               randomAccessFile.seek(startByte);
               //坑爹地方四：判断是否到了最后不足4096（buff的length）个byte这个逻辑（(transmitted + len) <= contentLength）要放前面！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
               //不然会会先读取randomAccessFile，造成后面读取位置出错，找了一天才发现问题所在
               //此处的原作者意思逻辑就是  (len = randomAccessFile.read(buff)) 每次读取4096个字节 eg 文件剩余2000 读4096 意味着 有2096
               //是空的  那么前端解析的时候就会出错  所以此处作者加了(transmitted + len) <= contentLength
               //条件判断
               while ((transmitted + len) <= contentLength && (len = randomAccessFile.read(buff)) != -1) {
                   outputStream.write(buff, 0, len);
                   transmitted += len;
               }
               //处理不足buff.length部分
               if (transmitted < contentLength) {
                   len = randomAccessFile.read(buff, 0, (int) (contentLength - transmitted));
                   outputStream.write(buff, 0, len);
                   transmitted += len;
               }

               outputStream.flush();
               response.flushBuffer();
               randomAccessFile.close();
               log.info("下载完毕：" + startByte + "-" + endByte + "：" + transmitted);
           } catch (ClientAbortException e) {
               log.warn("用户停止下载：" + startByte + "-" + endByte + "：" + transmitted);
               //捕获此异常表示拥护停止下载
           } catch (IOException e) {
               e.printStackTrace();
               log.error("用户下载IO异常，Message：{}", e.getLocalizedMessage());
           }finally {
               try {
                   if (randomAccessFile != null) {
                       randomAccessFile.close();
                   }
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }///end try
       }
   }






}
