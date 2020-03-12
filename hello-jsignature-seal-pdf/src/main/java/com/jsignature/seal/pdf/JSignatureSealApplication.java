package com.jsignature.seal.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 流程步骤： 1.绘制印章 2.生成PKCS12 3.绘制PDF模板 4.生成填充数据的PDF 5.盖章
 * 参考链接:https://blog.csdn.net/qq_30336433/article/details/83749991
 * @author ys
 * @topic
 * @date 2020/3/12 16:10
 */
@SpringBootApplication
public class JSignatureSealApplication {

    public static void main(String[] args) {
        SpringApplication.run(JSignatureSealApplication.class,args);
    }
}
