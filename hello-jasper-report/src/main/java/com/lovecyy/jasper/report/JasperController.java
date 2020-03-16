package com.lovecyy.jasper.report;

import net.sf.jasperreports.engine.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author ys
 * @topic
 * @date 2020/3/16 14:30
 */
@RestController
public class JasperController {

    @GetMapping("/testJasper")
    public void createPdf(HttpServletRequest request, HttpServletResponse response) throws IOException, JRException {
        ServletOutputStream outputStream = response.getOutputStream();
        //引入jasper文件
        Resource resource= new ClassPathResource("/templates/test.jasper");
        FileInputStream fis = new FileInputStream(resource.getFile());
        //2.创建jasperpringt 向jasper文件中填充数据
        /**
         * fis jasper 文件输入流
         * new HashMap  向模板中输入参数
         * JasperDataSource 数据源（和数据库的数据源不同）
         *    填充模板的数据来源（connection，javabean map）
         *    填充空数据源JREmptyDataSource
         */
        JasperPrint jasperPrint = JasperFillManager.fillReport(fis, new HashMap<>(), new JREmptyDataSource());
        //3将JasperPrint以pdf输出
        JasperExportManager.exportReportToPdfStream(jasperPrint,outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
