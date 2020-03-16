package com.lovecyy.poi;

import com.lovecyy.hello.poi.PoiApplication;
import org.apache.poi.ss.usermodel.*;
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
public class PoiTest02 {

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
        //  4.样式处理
        //    创建样式对象
        CellStyle style = wb.createCellStyle();
        style.setBorderTop(BorderStyle.DASH_DOT);//上边框
        style.setBorderBottom(BorderStyle.DASH_DOT);//下边框
        style.setBorderLeft(BorderStyle.DASH_DOT);//左边框
        style.setBorderRight(BorderStyle.DASH_DOT);//右边框
        //    创建字体对象
        Font font = wb.createFont();
        font.setFontName("华文行楷"); //字体
        font.setFontHeightInPoints((short) 28);//字号
        style.setFont(font);
        //行高 列宽
        row.setHeightInPoints(50);//行高
        sheet.setColumnWidth(2,31*256);//列宽  1 是索引  2.字符宽度  列宽31 此处设置
        //居中显示
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        //像单元格设置樣式
        cell.setCellStyle(style);
        //3.文件流
        FileOutputStream pis = new FileOutputStream("E:\\idea\\base\\text.xlsx");
        //4.写入文件
        wb.write(pis);
        //5.关闭流
        pis.close();

    }
}
