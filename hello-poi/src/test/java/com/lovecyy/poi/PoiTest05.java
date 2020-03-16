package com.lovecyy.poi;

import com.lovecyy.hello.poi.PoiApplication;

import com.lovecyy.hello.poi.handler.SheetHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;
import java.util.Iterator;
/**
 * @author ys
 * @topic 使用事件模型 解析百万数据excel报表
 * @date 2020/3/15 18:41
 */
@SpringBootTest(classes = PoiApplication.class)
@RunWith(SpringRunner.class)
public class PoiTest05 {

    @Test
    public void context() throws Exception {
     String path="E:\\idea\\base\\hello-poi\\src\\main\\resources\\demo.xlsx";
     //根据excel报表获取OPCPackage
        OPCPackage opcPackage = OPCPackage.open(path, PackageAccess.READ);
        //创建XSSFREADER
        XSSFReader reader = new XSSFReader(opcPackage);
        //获取SharedStringTable对象
        SharedStringsTable table = reader.getSharedStringsTable();
        //获取StyleTable对象
        StylesTable stylesTable = reader.getStylesTable();
        //创建Sax的xmlReader对象
        XMLReader xmlReader= XMLReaderFactory.createXMLReader();
     //注册事件处理器
        XSSFSheetXMLHandler xmlHandler = new XSSFSheetXMLHandler(stylesTable,table,new SheetHandler(),false);
        xmlReader.setContentHandler(xmlHandler);
     //逐行读取
        XSSFReader.SheetIterator sheetsData = (XSSFReader.SheetIterator) reader.getSheetsData();
        while (sheetsData.hasNext()){
            InputStream stream = sheetsData.next();
            InputSource inputSource = new InputSource(stream);
            xmlReader.parse(inputSource);
        }
    }


}
