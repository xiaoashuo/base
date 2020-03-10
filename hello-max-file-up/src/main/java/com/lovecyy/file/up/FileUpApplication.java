package com.lovecyy.file.up;

import com.lovecyy.file.up.example3.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ys
 * @topic
 * @date 2020/3/8 13:00
 */
@SpringBootApplication
public class FileUpApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(FileUpApplication.class,args);
    }

    @Autowired
    private FileUploadUtils fileUploadUtils;

    @Override
    public void run(String... args) throws Exception {
        fileUploadUtils.deleteAll();
    }
}
