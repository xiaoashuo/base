package com.lovecyy.face;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.util.Base64Util;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Base64Utils;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ys
 * @topic
 * @date 2020/3/16 16:12
 */
@SpringBootTest(classes = FaceApplication.class)
@RunWith(SpringRunner.class)
public class FaceTest {
    private static final String API_ID="";
    private static final String API_KEY="";
    private static final String SECRET_KEY="";
    AipFace client=null;
    @Before
    public void init(){
        System.out.println("开始初始化");
        client=client=new AipFace(API_ID,API_KEY,SECRET_KEY);
    }

    /**
     * 人脸注册 向人脸库添加人脸
     * @throws IOException
     */
    @Test
    public void testFaceRegister() throws IOException {
        //参数设置
        HashMap<String, String> options = new HashMap<String, String>();
        //活体检测
        options.put("liveness_control", "LOW");
        //图片质量
        options.put("quality_control", "NORMAL");
        String path="E:\\idea\\base\\hello-face\\src\\main\\resources\\001.png";
        //上传图片2中格式 url  base64
        System.out.println(Paths.get(path));
        byte[] bytes= Files.readAllBytes(Paths.get(path));
        String encode = Base64Util.encode(bytes);
        // 1. 图片url或base64 2.图片类型 URL BASE64
        //3. 组id(固定字符串) 4. 用户id 5.配置
        JSONObject res = client.addUser(encode, "BASE64", "face_test", "1000", options);
        System.out.println(res.toString());
    }

    /**
     * 人脸检测 判断图片中是否有面部信息
     */
    @Test
    public void testFaceCheck() throws IOException {
        //构造图片
        String path="E:\\idea\\base\\hello-face\\src\\main\\resources\\logo.jpg";
        byte[] bytes= Files.readAllBytes(Paths.get(path));
        String encode = Base64Util.encode(bytes);
        //调用api方法进行人脸检测
        JSONObject res = client.detect(encode, "BASE64", null);
        System.out.println(res);
    }

    /**
     * 人脸搜索 根据用户上传图片 和指定人脸库的进行比对  获取相似度最高 或某些个
     *  说明  返回值是数组  只需要第一条 相似度最高的数据
     *        score 相似度评分 80分以上 可以认为同一用户
     */
    @Test
    public void testFaceSearch() throws IOException {
        //构造图片
        String path="E:\\idea\\base\\hello-face\\src\\main\\resources\\003.png";
        byte[] bytes= Files.readAllBytes(Paths.get(path));
        String encode = Base64Util.encode(bytes);
        //人脸搜索
        JSONObject res = client.search(encode, "BASE64", "face_test", null);
        System.out.println(res.toString(2));

    }


    /**
     * 人脸更新 更新人脸库中的照片
     *
     */
    @Test
    public void testFaceUpdate() throws IOException {
        //参数设置
        HashMap<String, String> options = new HashMap<String, String>();
        //活体检测
        options.put("liveness_control", "LOW");
        //图片质量
        options.put("quality_control", "NORMAL");
        String path="E:\\idea\\base\\hello-face\\src\\main\\resources\\002.png";
        //上传图片2中格式 url  base64
        System.out.println(Paths.get(path));
        byte[] bytes= Files.readAllBytes(Paths.get(path));
        String encode = Base64Util.encode(bytes);
        // 1. 图片url或base64 2.图片类型 URL BASE64
        //3. 组id(固定字符串) 4. 用户id 5.配置
        JSONObject res = client.updateUser(encode, "BASE64", "face_test", "1000", options);
        System.out.println(res.toString());
    }

}
