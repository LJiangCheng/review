package com.ljc.review.common.image;

import com.ljc.review.common.image.resize.ResizeImage;
import com.ljc.review.common.image.watermark.ImageProcessor;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class TestImage {

    /**
     * 图片水印测试
     */
    @Test
    public void testWaterMarker() {
        //水印图
        File waterMarkFile = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\water.png");
        //原图
        File srcFile = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\400.jpg");
        //目标文件
        File destFile = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\500.jpg");
        ImageProcessor.imageAddWaterMark(srcFile, waterMarkFile, destFile, 1, 0.3F, 100);
    }

    /**
     * 图片缩放测试
     * @throws IOException
     */
    @Test
    public void testResize() throws IOException {
        String sourcePath = "E:\\resize\\d3a608b0fb.jpg";
        File file = new File(sourcePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArr = new byte[327091];
        fis.read(byteArr);
        ResizeImage.resize(byteArr);
    }

}
