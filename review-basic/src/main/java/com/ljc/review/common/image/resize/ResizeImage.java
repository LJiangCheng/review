package com.ljc.review.common.image.resize;

import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ResizeImage {

    private static File drawImage(Image image, String filePath, int w, int h, int x, int y, ImageObserver observer) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = bufferedImage.getGraphics();
        //image.getScaledInstance(w, h, Image.SCALE_SMOOTH)：根据不同的压缩策略缩放图片
        graphics.drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, observer);
        graphics.dispose();
        File file = new File(filePath);
        // 获取文件扩展名
        String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
        ImageIO.write(bufferedImage, ext, file);
        return file;
    }

    /**
     * 方式2：从ImageIcon重新画出BufferedImage
     */
    public static void resizeOne(byte[] byteArr) throws IOException {
        ImageIcon imageIcon = new ImageIcon(byteArr);
        //Image image = imageIcon.getImage();
        BufferedImage image = new BufferedImage(imageIcon.getIconWidth(), imageIcon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = image.createGraphics();
        g.drawImage(imageIcon.getImage(), 0, 0, null);
        g.dispose();
        //Image image = ImageIO.read(new File(sourcePath));
        for (int i = 1; i < 8; i++) {
            String targetPath = "E:\\resize\\" + i * 200 + ".jpg";
            drawImage(image, targetPath, i * 200, i * 200, 0, 0, null);
        }
        System.out.println("success!");
    }

    /**
     * 方式1：直接构建BufferedImage
     */
    public static void resize(byte[] byteArr) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArr));
        System.out.println("原图宽：" + image.getWidth());
        System.out.println("原图高：" + image.getHeight());
        for (int i = 1; i < 8; i++) {
            String targetPath = "E:\\resize\\" + i * 250 + ".jpg";
            drawImage(image, targetPath, i * 250, i * 250, 0, 0, null);
        }
        System.out.println("success!");
    }

}





















