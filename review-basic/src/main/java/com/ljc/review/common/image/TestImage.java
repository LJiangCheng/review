package com.ljc.review.common.image;

import cn.hutool.core.img.ImgUtil;
import com.ljc.review.common.image.resize.ResizeImage;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestImage {

    public static File drawImage(Image image, String filePath, int w, int h, int x, int y, ImageObserver observer) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        graphics.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, observer);
        File file = new File(filePath);
        // 获取文件扩展名
        String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
        ImageIO.write(bufferedImage, ext, file);
        //释放资源
        graphics.dispose();
        return file;
    }

    public static void printMemory(long l1, long l2) {
        Runtime runtime = Runtime.getRuntime();
        //long max = runtime.maxMemory() / 1024 / 1024;
        long total = runtime.totalMemory() / 1024 / 1024;
        long free = runtime.freeMemory() / 1024 / 1024;
        long used = total - free;
        System.out.println("used: " + used + "M   free: " + free + "M " + "cost：" + (l2 - l1) + "ms");
    }

    @Test
    public void tu() throws IOException {
        long l1 = System.currentTimeMillis();
        Thumbnails.of(new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\2.jpg"))
                .size(910, 578)
                //.rotate(90)
                .watermark(Positions.CENTER_LEFT, ImageIO.read(new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\water.png")), 1f)
                .outputQuality(0.8)
                .toFile(new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\2\\redlogon" + 1 + ".jpg"));
        long l2 = System.currentTimeMillis();
        printMemory(l1, l2);
    }

    @Test
    public void logoMemory() throws IOException {
        File source = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\2.jpg");
        File logo = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\water.png");
        FileInputStream srcStream;
        FileOutputStream destStream;
        for (int i = 0; i < 30; i++) {
            long l1 = System.currentTimeMillis();
            File destFile = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\2\\redlogon" + i + ".jpg");
            srcStream = new FileInputStream(source);
            destStream = new FileOutputStream(destFile);
            ImgUtil.pressImage(srcStream, destStream, ImageIO.read(logo), 0, 0, (float) 1);
            long l2 = System.currentTimeMillis();
            printMemory(l1, l2);
        }
    }

    @Test
    public void mem() throws InterruptedException {
        Thread.sleep(1000000000);
    }

    /**
     * 全图水印内存测试
     */
    @Test
    public void fullLogoMemory() throws IOException, InterruptedException {
        /*for (int j = 1; j < 3; j++) {
            final int n = j;
            new Thread(() -> {
                for (int i = 0; i < 3; i++) {
                    long l1 = System.currentTimeMillis();
                    //水印图
                    File logo = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\water_details.png");
                    //原图
                    File source = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\2.jpg");
                    //目标文件
                    File destFile = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\2\\redlogon" + i + "_" + n + ".jpg");
                    if (destFile.exists()) destFile.delete();
                    try {
                        ImageProcessor.oldFullLogo(source, logo, destFile, 1, 0.1F, 85);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    long l2 = System.currentTimeMillis();
                    printMemory(l1, l2);
                }
            }).start();
        }
        Thread.sleep(40000);
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                for(int j = 0;j<30;j++) {
                    synchronized (this){
                        System.out.print("====>测试 ");
                        printMemory(0, 0);
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignore) {}
                }
            }).start();
        }*/
        Thread.sleep(1000000000);
    }

    /**
     * 图片水印测试
     */
    @Test
    public void testWaterMarker() throws IOException {
        //水印图
        File logo = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\water_details.png");
        //原图
        File source = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\2.jpg");
        //目标文件
        File destFile = new File("C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\redlogo.jpg");
        if (destFile.exists()) destFile.delete();
        //缩放
        String fullPath = "C:\\Users\\toolmall\\Desktop\\watermaker\\水印\\2_scale.jpg";
        BufferedImage image = ImageIO.read(source);
        int height = image.getHeight();
        int width = image.getWidth();
        if (width > 910) {
            //将宽度缩小为910，同时等比缩小高度
            drawImage(image, fullPath, 910, Math.round(height * (910F / width)), 0, 0, null);
            image.getGraphics().dispose();
            //ImageProcessor.fullLogo(new File(fullPath), logo, destFile, 1, 0.1F, 85);
        } else {
            //ImageProcessor.fullLogo(source, logo, destFile, 1, 0.1F, 85);
        }
        printMemory(0, 0);
    }

    @Test
    public void imageReadTest() throws IOException {
        for (int i = 0; i < 30; i++) {
            /*//BufferedImage image = ImageIO.read(new File("C:\\Users\\toolmall\\Desktop\\watermaker\\webp\\2.jpg"));
            Iterator<ImageReader> jpgReader = ImageIO.getImageReadersByFormatName("jpg");
            ImageReader reader = jpgReader.next();
            ImageInputStream iis = ImageIO.createImageInputStream(new File("C:\\Users\\toolmall\\Desktop\\watermaker\\webp\\2.jpg"));
            reader.setInput(iis, true);
            System.out.print("width:" + reader.getWidth(0));
            System.out.print("  height:" + reader.getHeight(0) + "  ");
            printMemory(0, 0);
            //image.getGraphics().dispose();*/
        }
    }

    /**
     * 图片缩放测试
     *
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
