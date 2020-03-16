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
 * @topic 读取excel并解析
 *    //sheet.getLastRowNum() 得到最后一行的索引
 *    //row.getLastCellNum() 得到最后一个单元格的号码
 * @date 2020/3/15 18:41
 */
@SpringBootTest(classes = PoiApplication.class)
@RunWith(SpringRunner.class)
public class PoiTest04 {

    @Test
    public void context() throws Exception {
       //1.根据excel创建工作簿
        Workbook wb=new XSSFWorkbook("E:\\idea\\base\\demo.xlsx");
       // 2.获取sheet
        Sheet sheet = wb.getSheetAt(0);
        //3.获取sheet中的每一行 和每一个单元格

        for (int rowNum = 0; rowNum <=sheet.getLastRowNum(); rowNum++) {
            Row row = sheet.getRow(rowNum);
            StringBuilder sb = new StringBuilder();
            for (int cellNum = 2; cellNum < row.getLastCellNum(); cellNum++) {
                  //根据索引获取每一个单元
                Cell cell = row.getCell(cellNum);
                //获取每一个单元格的内容
                Object value = getCellValue(cell);
                sb.append(value).append("-");
            }
            System.out.println(sb.toString());
        }

    }

    public static Object getCellValue(Cell cell){
       //获取单元格类型
        CellType cellType = cell.getCellTypeEnum();
        Object value=null;
        //根据单元格类型 获取数据
        switch (cellType){
            case STRING :
                value=cell.getStringCellValue();
                break;
            case BOOLEAN:
                value=cell.getBooleanCellValue();
                break;
            case NUMERIC:

                if (DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value=cell.getDateCellValue();
                }else{
                    //数字
                    value=cell.getNumericCellValue();
                }

                break;
            case FORMULA:
                value=cell.getCellFormula();
                break;
                default:
                    break;
        }
        return value;
    }
}
