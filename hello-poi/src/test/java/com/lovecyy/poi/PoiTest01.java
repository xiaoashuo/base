package com.lovecyy.poi;

import com.lovecyy.hello.poi.PoiApplication;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;

/**
 * @author ys
 * @topic
 * @date 2020/3/15 18:41
 */
@SpringBootTest(classes = PoiApplication.class)
@RunWith(SpringRunner.class)
public class PoiTest01 {

    @Test
    public void context() throws Exception {
       //1.创建工作簿
        Workbook wb = new XSSFWorkbook();
        //2.创建sheet
        Sheet sheet = wb.createSheet("text");
        //  1.创建行对象 索引从0开始
        Row row = sheet.createRow(2);
        //  2.创建单元格 0开始
        Cell cell = row.createCell(2);
        //  3.向单元格写入内容
        cell.setCellValue("您好");
        //3.文件流
        FileOutputStream pis = new FileOutputStream("E:\\idea\\base\\text.xlsx");
        //4.写入文件
        wb.write(pis);
        //5.关闭流
        pis.close();

    }
}
