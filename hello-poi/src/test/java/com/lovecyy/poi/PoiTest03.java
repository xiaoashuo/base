package com.lovecyy.poi;

import com.lovecyy.hello.poi.PoiApplication;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * @author ys
 * @topic 向单元格插入图片
 * @date 2020/3/15 18:41
 */
@SpringBootTest(classes = PoiApplication.class)
@RunWith(SpringRunner.class)
public class PoiTest03 {

    @Test
    public void context() throws Exception {
       //1.创建工作簿
        Workbook wb = new XSSFWorkbook();
        //2.创建sheet
        Sheet sheet = wb.createSheet("text");
        //读取图片流
        FileInputStream stream=new FileInputStream("E:\\idea\\base\\logo.jpg");
        //将图片转为二进制数组
        byte[] bytes = IOUtils.toByteArray(stream);
        stream.read(bytes);
        //向poi内存添加一张图片 返回图片在图片集合中的下标
        int index = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);//1.图片二进制数据  2.图片类型
        //绘制图片工具类
        CreationHelper helper = wb.getCreationHelper();
        //创建绘图对象
        Drawing<?> patriarch = sheet.createDrawingPatriarch();
        //创建锚点 设置图片坐标
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setRow1(0);
        anchor.setCol1(0);
        //绘制图片 1.图片位置 2. 图片的索引
        Picture picture = patriarch.createPicture(anchor, index);
        picture.resize();//自适应渲染
        //3.文件流
        FileOutputStream pis = new FileOutputStream("E:\\idea\\base\\text.xlsx");
        //4.写入文件
        wb.write(pis);
        //5.关闭流
        pis.close();

    }
}
