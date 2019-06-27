package com.ljc.review.common.image;

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

    public static File drawImage(Image image, String filePath, int w, int h, int x, int y, ImageObserver observer) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        bufferedImage.getGraphics().drawImage(image.getScaledInstance(w, h, Image.SCALE_SMOOTH), x, y, observer);
        File file = new File(filePath);
        // 获取文件扩展名
        String ext = filePath.substring(filePath.lastIndexOf(".") + 1);
        ImageIO.write(bufferedImage, ext, file);
        return file;
    }

    @Test
    public void testResize() throws IOException {
        String sourcePath = "E:\\resize\\d3a608b0fb.jpg";
        File file = new File(sourcePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArr = new byte[327091];
        fis.read(byteArr);
        resize(byteArr);
    }

    /**
     * 方式1：直接构建BufferedImage
     */
    private void resize(byte[] byteArr) throws IOException {
        BufferedImage image = ImageIO.read(new ByteArrayInputStream(byteArr));
        for (int i = 1; i < 8; i++) {
            String targetPath = "E:\\resize\\" + i * 250 + ".jpg";
            drawImage(image, targetPath, i * 250, i * 250, 0, 0, null);
        }
        System.out.println("success!");
    }

    /**
     * 方式2：从ImageIcon重新画出BufferedImage
     */
    private void resizeOne(byte[] byteArr) throws IOException {
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

}





















