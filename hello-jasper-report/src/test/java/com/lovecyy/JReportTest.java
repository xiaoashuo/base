package com.lovecyy;

import com.lovecyy.jasper.report.JasperReportApplication;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.view.JasperViewer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @author ys
 * @topic
 * @date 2020/3/16 13:32
 */

@SpringBootTest(classes = JasperReportApplication.class)
@RunWith(SpringRunner.class)
public class JReportTest {

    @Test
    public void context() throws IOException {
   /*     String property = System.getProperty("user.dir");
        System.out.println(property);
        Resource resource = new ClassPathResource("/test01.jrxml");
        File file = resource.getFile()/;
        System.out.println(file.getName());*/
        //createJrprint();
        showPdf();
    }

    public static void main(String[] args) throws IOException {
        createJasper();
    }
    //1.将pdf模板编译为jasper
    public static void createJasper() throws IOException {

        try{
            String path = "E:\\idea\\base\\hello-jasper-report\\src\\main\\resources\\test01.jrxml";
            JasperCompileManager.compileReportToFile(path);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //2.将jasper文件和数据填充 获取jrprint
    public static void createJrprint(){
        try{
            String path = "E:\\idea\\base\\hello-jasper-report\\src\\main\\resources\\test01.jasper";
            //通过空参数和空数据源进行填充
            JasperFillManager.fillReportToFile(path,new HashMap(),new JREmptyDataSource());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //3.将文件进行进行输出
    public static void showPdf(){
        try{
            String path = "E:\\idea\\base\\hello-jasper-report\\src\\main\\resources\\test01.jrprint";
            JasperViewer.viewReport(path,false);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
