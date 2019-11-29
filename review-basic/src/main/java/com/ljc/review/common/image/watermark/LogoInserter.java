package com.ljc.review.common.image.watermark;

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

        int mixWidth = ((srcWidth - initialX) >= logoWidth) ? logoWidth : (srcWidth - initialX);
        int mixHight = ((srcHeight - initialY) >= logoHeight) ? logoHeight : (srcHeight - initialY);
        for (int i = 0; i < mixWidth; i++) {
            for (int j = 0; j < mixHight; j++) {
                int x = initialX + i;
                int y = initialY + j;
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

    public static ImageData insertLogo(ImageData srcImg, ImageData logo, int initialX, int initialY){
        int srcWidth = srcImg.getWidth(), srcHeight = srcImg.getHeight();
        int logoWidth = logo.getWidth(), logoHeight = logo.getHeight();
        byte[] a1 = srcImg.getAchannel(), r1 = srcImg.getRchannel(), g1 = srcImg.getGchannel(), b1 = srcImg.getBchannel();
        byte[] a2 = logo.getAchannel(), r2 = logo.getRchannel(), g2 = logo.getGchannel(), b2 = logo.getBchannel();

        int indexSrc, indexLogo;

        int mixWidth = ((srcWidth - initialX) >= logoWidth) ? logoWidth : (srcWidth - initialX);
        int mixHight = ((srcHeight - initialY) >= logoHeight) ? logoHeight : (srcHeight - initialY);;

        for (int i = 0; i < mixWidth; i++) {
            for (int j = 0; j < mixHight; j++) {
                indexSrc = (initialY + j) * srcWidth + initialX + i;
                indexLogo = j * logoWidth + i;
                r1[indexSrc] = (byte) (((r1[indexSrc] & 0xff) * (255 - (a2[indexLogo] & 0xff)) + (r2[indexLogo] & 0xff) * (a2[indexLogo] & 0xff)) / 255);
                g1[indexSrc] = (byte) (((g1[indexSrc] & 0xff) * (255 - (a2[indexLogo] & 0xff)) + (g2[indexLogo] & 0xff) * (a2[indexLogo] & 0xff)) / 255);
                b1[indexSrc] = (byte) (((b1[indexSrc] & 0xff) * (255 - (a2[indexLogo] & 0xff)) + (b2[indexLogo] & 0xff) * (a2[indexLogo] & 0xff)) / 255);
            }
        }
        int[] pixel = new int[srcWidth * srcHeight];
        for (int i = 0; i < srcWidth * srcHeight; i++) {
            pixel[i] = ((a1[i] & 0xff) << 24) | ((r1[i] & 0xff) << 16) | ((g1[i] & 0xff) << 8) | b1[i] & 0xff;
        }
        srcImg.setChannels(a1, r1, g1, b1);
        srcImg.setPixel(pixel);

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
