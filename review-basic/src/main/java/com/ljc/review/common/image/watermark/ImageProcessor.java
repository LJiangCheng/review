package com.ljc.review.common.image.watermark;

import java.io.File;

/**
 * 添加图片水印
 * ljc 19-08-09
 */
public class ImageProcessor {

    /**
     * 为整张图片打满水印
     * @param srcFile  原始图片文件
     * @param waterMarkFile 水印图片文件
     * @param dstFile 添加水印后的文件
     * @param sigma 西格玛值
     */
    public static void imageAddWaterMark(File srcFile, File waterMarkFile, File dstFile, double sigma, float weight, int Quality) {
        ImageUtil imageUtil = new ImageUtil();
        // 原始图片
        ImageData srcImage = imageUtil.readImage(srcFile);
        // 水印图片
        ImageData waterMarkImage = imageUtil.readImage(waterMarkFile);
        // 全图打上水印
        int srcWidth = srcImage.getWidth(), srcHeight = srcImage.getHeight();
        int waterMarkWidth = waterMarkImage.getWidth(), waterMarkHeight = waterMarkImage.getHeight();
        for (int y=0; y<srcHeight; y+=waterMarkHeight) {
            for (int x=0; x<srcWidth; x+=waterMarkWidth) {
                srcImage = LogoInserter.insertLogo(srcImage, waterMarkImage, x, y);
            }
        }
        // 图片锐化
        Sharpen sh = new Sharpen();
        ImageData tempImg = sh.ImageSharpen(srcImage, sigma, weight);
        // 输出图片
        imageUtil.writeImage(tempImg, dstFile, Quality);
    }

}



































