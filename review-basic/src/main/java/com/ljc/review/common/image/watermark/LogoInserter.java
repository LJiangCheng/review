package com.ljc.review.common.image.watermark;


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

}
