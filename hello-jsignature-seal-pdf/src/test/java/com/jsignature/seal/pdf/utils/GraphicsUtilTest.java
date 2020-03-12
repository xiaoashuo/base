package com.jsignature.seal.pdf.utils;

import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author ys
 * @topic
 * @date 2020/3/12 16:16
 */
public class GraphicsUtilTest {

    @Test
    public void getSeal() {
        int canvasWidth = 400;
        int canvasHeight = 400;
        double lineArc = 80*(Math.PI/180);//角度转弧度
        String savepath = "seal.png";
        SimpleDateFormat format = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
        String head = "中国科学院信息工程研究所";
        String foot = "受理专用章";
        String center = format.format(new Date());
        BufferedImage image = GraphicsUtil.getCircleSeal(head, center, foot, canvasWidth, canvasHeight, lineArc);

        try
        {
            ImageIO.write(image, "PNG", new File(savepath));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Test
    public void getRectangleSeal(){
        GraphicsUtil.getRectangleSignTextImg("华佗", "在线医院", "2018.01.01",   "sign.jpg");
    }
}
