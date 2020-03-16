package com.lovecyy.hello.poi.handler;

import com.lovecyy.hello.poi.pojo.PoiEntity;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * @author ys
 * @topic 自定义事件处理器
 * @date 2020/3/16 10:54
 */
public class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {
    private PoiEntity entity;
    /**
     * 开始解析每一行
     * @param i
     */
    @Override
    public void startRow(int i) {
      //实列化对象
        if (i>0){
            entity=new PoiEntity();
        }
    }

    /**
     * 结束解析某一行时触发
     * @param i
     */
    @Override
    public void endRow(int i) {
      //使用对象进行业务操作
        System.out.println(entity);
    }

    /**
     *
     * 对行中的每一个表格进行处理
     * @param cellReference 单元格名称
     * @param value 单元格数据
     * @param xssfComment 批注
     */
    @Override
    public void cell(String cellReference, String value, XSSFComment xssfComment) {
       //对对象属性赋值
        if (entity!=null){
            String pix = cellReference.substring(0, 1);
            switch (pix){
                case "A":
                    entity.setId(value);
                    break;
                case "B":
                    entity.setBreast(value);
                    break;
                case "C":
                    entity.setAdipocytes(value);
                    break;
                case "D":
                    entity.setNegative(value);
                    break;
                case "E":
                    entity.setStaining(value);
                    break;
                case "F":
                    entity.setSupportive(value);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void headerFooter(String s, boolean b, String s1) {

    }


}
