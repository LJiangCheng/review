package com.ljc.review.common.image.watermark;


import cn.hutool.core.img.ImgUtil;

import java.awt.image.BufferedImage;

/**
 * Created by Rocky on 6/10/2017.
 */

public class LogoInserter {
    /**
     * 图片上增加水印功能
     * @param srcImg
     * @param logo
     * @param initialX
     * @param initialY
     * @return
     */
    public static BufferedImage insertLogo(BufferedImage srcImg, BufferedImage logo, int initialX, int initialY){
        int srcWidth = srcImg.getWidth(), srcHeight = srcImg.getHeight();
        int logoWidth = logo.getWidth(), logoHeight = logo.getHeight();

        /*int[] p1 = srcImg.getPixels();
        int[] p2 = logo.getPixels();*/
        int index1, index2;
        int mixWidth = ((srcWidth - initialX) >= logoWidth) ? logoWidth : (srcWidth - initialX);
        int mixHight = ((srcHeight - initialY) >= logoHeight) ? logoHeight : (srcHeight - initialY);
        for (int i = 0; i < mixWidth; i++) {
            for (int j = 0; j < mixHight; j++) {
                index1 = (initialY + j) * srcWidth + initialX + i;
                index2 = j * logoWidth + i;
                int x = initialX + i;
                int y = initialY + j;
                /*p1[index1] =
                        ((a(p1,index1) & 0xff) << 24) |
                        ((rlogo(p1,p2,index1,index2) & 0xff) << 16) |
                        ((glogo(p1,p2,index1,index2) & 0xff) << 8) |
                        blogo(p1,p2,index1,index2) & 0xff;*/
                int rgb =
                        ((a(srcImg.getRGB(x,y)) & 0xff) << 24) |
                                ((rlogo(srcImg.getRGB(x,y),logo.getRGB(i,j)) & 0xff) << 16) |
                                ((glogo(srcImg.getRGB(x,y),logo.getRGB(i,j)) & 0xff) << 8) |
                                blogo(srcImg.getRGB(x,y),logo.getRGB(i,j)) & 0xff;
                srcImg.setRGB(x, y, rgb);
            }
        }

        return srcImg;
    }

    /**
     * 根据原图和水印图的argb通道信息分别计算出水印图的rgb信息 PS:a通道只需要在原图的基础上做运算就行
     */
    private static int rlogo(int[] p1, int[] p2, int index1, int index2) {
        return ((r(p1, index1) & 0xff) * (255 - (a(p2, index2) & 0xff)) + (r(p2, index2) & 0xff) * (a(p2, index2) & 0xff)) / 255;
    }

    private static int glogo(int[] p1, int[] p2, int index1, int index2) {
        return ((g(p1, index1) & 0xff) * (255 - (a(p2, index2) & 0xff)) + (g(p2, index2) & 0xff) * (a(p2, index2) & 0xff)) / 255;
    }

    private static int blogo(int[] p1, int[] p2, int index1, int index2) {
        return ((b(p1, index1) & 0xff) * (255 - (a(p2, index2) & 0xff)) + (b(p2, index2) & 0xff) * (a(p2, index2) & 0xff)) / 255;
    }

    private static int rlogo(int rgb1,int rgb2) {
        return ((r(rgb1) & 0xff) * (255 - (a(rgb2) & 0xff)) + (r(rgb2) & 0xff) * (a(rgb2) & 0xff)) / 255;
    }

    private static int glogo(int rgb1,int rgb2) {
        return ((g(rgb1) & 0xff) * (255 - (a(rgb2) & 0xff)) + (g(rgb2) & 0xff) * (a(rgb2) & 0xff)) / 255;
    }

    private static int blogo(int rgb1,int rgb2) {
        return ((b(rgb1) & 0xff) * (255 - (a(rgb2) & 0xff)) + (b(rgb2) & 0xff) * (a(rgb2) & 0xff)) / 255;
    }

    /**
     * 通过pixel信息分别计算图像a r g b通道的值
     */
    private static int a(int[] p, int index) {
        return (p[index]&0xff000000) >> 24;
    }

    private static int r(int[] p, int index) {
        return (p[index]&0xff0000) >> 16;
    }

    private static int g(int[] p, int index) {
        return (p[index]&0xff00) >> 8;
    }

    private static int b(int[] p, int index) {
        return p[index]&0xff;
    }

    private static int a(int rgb) {
        return (rgb&0xff000000) >> 24;
    }

    private static int r(int rgb) {
        return (rgb&0xff0000) >> 16;
    }

    private static int g(int rgb) {
        return (rgb&0xff00) >> 8;
    }

    private static int b(int rgb) {
        return rgb&0xff;
    }

}
