package com.jsignature.seal.pdf.utils;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.AcroFields.Item;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * @author ys
 * @topic
 * @date 2020/3/12 18:48
 */
public class PDFUtils {
    /**
     * @param fields
     * @param data
     * @throws IOException
     * @throws DocumentException
     */
    private static void fillData(AcroFields fields, Map<String, String> data) throws IOException, DocumentException {
        List<String> keys = new ArrayList<String>();
        Map<String, Item> formFields = fields.getFields();
        for (String key : data.keySet()) {
            if(formFields.containsKey(key)){
                String value = data.get(key);
                fields.setField(key, value); // 为字段赋值,注意字段名称是区分大小写的
                keys.add(key);
            }
        }
        Iterator<String> itemsKey = formFields.keySet().iterator();
        while(itemsKey.hasNext()){
            String itemKey = itemsKey.next();
            if(!keys.contains(itemKey)){
                fields.setField(itemKey, " ");
            }
        }
    }

    /**
     * @param templatePdfPath
     *            模板pdf路径
     * @param generatePdfPath
     *            生成pdf路径
     * @param data
     *            数据
     */
    public static String generatePDF(String templatePdfPath, String generatePdfPath, Map<String, String> data) {
        OutputStream fos = null;
        ByteArrayOutputStream bos = null;
        PdfReader reader=null;
        PdfStamper ps =null;
        try {
            reader = new PdfReader(templatePdfPath);
            bos = new ByteArrayOutputStream();
            /* 将要生成的目标PDF文件名称 */
            ps = new PdfStamper(reader, bos);
            /* 使用中文字体 */
            BaseFont bf = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",BaseFont.NOT_EMBEDDED);
            ArrayList<BaseFont> fontList = new ArrayList<BaseFont>();
            fontList.add(bf);
            /* 取出报表模板中的所有字段 */
            AcroFields fields = ps.getAcroFields();
            fields.setSubstitutionFonts(fontList);
            fillData(fields, data);
            /* 必须要调用这个，否则文档不会生成的  如果为false那么生成的PDF文件还能编辑，一定要设为true*/
            ps.setFormFlattening(true);
            ps.close();
            fos = new FileOutputStream(generatePdfPath);
            fos.write(bos.toByteArray());
            fos.flush();
            return generatePdfPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (reader != null) {
                reader.close();
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Map<String, String> data = new HashMap<String, String>();
        //key为pdf模板的form表单的名字，value为需要填充的值
        data.put("name", "张三");
        data.put("cardNo", "123456789");
        data.put("clinic", "外科门诊");
        data.put("cause", "不知道啊");
        data.put("result", "生死有命,富贵在天。");
        data.put("validate", "Yakir");
        generatePDF("E:\\idea\\base\\hello-jsignature-seal-pdf\\src\\main\\resources\\嘉兴市第十人民医院.pdf",
                "E:\\idea\\base\\hello-jsignature-seal-pdf\\src\\main\\resources\\filled.pdf", data );
    }
}
