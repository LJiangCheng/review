package com.ljc.review.common.image.resize;

import org.junit.Test;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResizeImage {

    public int countStep(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        return count(n, map);
    }

    private int count(int n, Map<Integer, Integer> map) {
        //递归终止的基本条件
        if (n < 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        //第一步只有两种情况。每一个n级台阶的可能走法相当于第一步走一阶和第一步走两阶的情况之和，最终会达成递归的终止条件
        int result;
        if (map.containsKey(n - 1)) {
            result = map.get(n - 1) + countStep(n - 2);
        } else if (map.containsKey(n - 2)) {
            result = countStep(n - 1) + map.get(n - 2);
        } else {
            result = countStep(n - 1) + countStep(n - 2);
        }
        map.put(n, result);
        return result;
    }

    public int count(int n, int step, int result) {
        //何时终止递归
        if (step == n) {
            return result;
        } else {
            //

            return count(n, ++step, result);
        }
    }


    @Test
    public void test() {
        System.out.println(reverse("1234567"));
    }

    public String reverse(String s) {
        return reverse(s, 0);
    }

    private String reverse
            (String s, int i) {
        int length = s.length();
        //反转完成标志
        int count = length / 2;
        if (i == count) {
            return s;
        } else {
            //反转第i次
            int index2 = length - 1 - i;
            char chari = s.charAt(i);
            char[] chars = s.toCharArray();
            chars[i] = chars[index2];
            chars[index2] = chari;
            return reverse(new String(chars), ++i);
        }
    }

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





















