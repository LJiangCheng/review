package com.ljc.review.common.image.watermark;

import com.ljc.review.common.image.TestImage;
import org.apache.logging.log4j.core.util.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 添加图片水印
 * ljc 19-08-09
 */
public class ImageProcessor {

    /**
     * 中心水印
     * @param srcFile  原始图片文件
     * @param waterMarkFile 水印图片文件
     * @param dstFile 添加水印后的文件
     * @param sigma 西格玛值
     */
    public static void centerLogo(File srcFile, File waterMarkFile, File dstFile, double sigma, float weight, int Quality) throws IOException {
        BufferedImage srcImage = ImageIO.read(srcFile);
        BufferedImage waterMarkImage = ImageIO.read(waterMarkFile);
        // 计算坐标地址
        int srcWidth = srcImage.getWidth(), srcHeight = srcImage.getHeight();
        int logoWidth = waterMarkImage.getWidth(), logoHeight = waterMarkImage.getHeight();
        int x = srcWidth / 2 - logoWidth / 2;
        int y = srcHeight / 2 - logoHeight / 2;
        if(x < 0) x = 0;
        if(y < 0) y = 0;
        LogoInserter.insertLogo(srcImage, waterMarkImage, x, y);
        ImageIO.write(srcImage, FileUtils.getFileExtension(dstFile), dstFile);
        srcImage.flush();
        waterMarkImage.flush();
    }

    /**
     * 全图水印
     * @param srcFile  原始图片文件
     * @param waterMarkFile 水印图片文件
     * @param dstFile 添加水印后的文件
     * @param sigma 西格玛值
     */
    public static void fullLogo(File srcFile, File waterMarkFile, File dstFile, double sigma, float weight, int Quality) throws IOException {
        BufferedImage srcImage = ImageIO.read(srcFile);
        BufferedImage waterMarkImage = ImageIO.read(waterMarkFile);
        // 全图打上水印
        int srcWidth = srcImage.getWidth(), srcHeight = srcImage.getHeight();
        int logoWidth = waterMarkImage.getWidth(), logoHeight = waterMarkImage.getHeight();
        for (int y=0; y<srcHeight; y+=logoHeight) {
            for (int x=0; x<srcWidth; x+=logoWidth) {
                LogoInserter.insertLogo(srcImage, waterMarkImage, x, y);
            }
        }
        ImageIO.write(srcImage, FileUtils.getFileExtension(dstFile), dstFile);
        srcImage.flush();
        waterMarkImage.flush();
    }

    public static void oldFullLogo(File srcFile, File waterMarkFile, File dstFile, double sigma, float weight, int Quality) throws IOException {
        ImageUtil imageUtil = new ImageUtil();
        // 原始图片
        ImageData srcImage = imageUtil.readImage(srcFile);
        // 水印图片
        ImageData waterMarkImage = imageUtil.readImage(waterMarkFile);
        // 全图打上水印
        int srcWidth = srcImage.getWidth(), srcHeight = srcImage.getHeight();
        int logoWidth = waterMarkImage.getWidth(), logoHeight = waterMarkImage.getHeight();
        for (int y=0; y<srcHeight; y+=logoHeight) {
            for (int x=0; x<srcWidth; x+=logoWidth) {
                LogoInserter.insertLogo(srcImage, waterMarkImage, x, y);
            }
        }
        // 图片锐化
        Sharpen sh = new Sharpen();
        ImageData tempImg = sh.ImageSharpen(srcImage, sigma, weight);
        // 输出图片
        imageUtil.writeImage(srcImage, dstFile, Quality);
    }

}



































