package com.ljc.review.common.image.watermark;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rocky on 6/7/2017.
 */
public class ImageUtil {

    /**
     * Read the source image to Object srcImg
     */

    public ImageData readImage (File srcFile) {
        ImageData srcImg = new ImageData();
        int[] pixel;
        if (!srcFile.exists()) {
            System.out.println(srcFile + " does not exist!");
        }
        try {
            BufferedImage bufImg = ImageIO.read(srcFile);
            int height = bufImg.getHeight();
            int width = bufImg.getWidth();
            srcImg.setSize(width,height);
            pixel = new int[width * height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    pixel[i + j * width] = bufImg.getRGB(i, j);
                }
            }
            srcImg.setPixel(pixel);
            bufImg.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return srcImg;
    }

    /**
     * Write the destination image to file.
     */

    public void writeImage (ImageData img, File dstFile, int Quality) {
        int width = img.getWidth();
        int height = img.getHeight();
        BufferedImage bf;
        String ext = FilenameUtils.getExtension(dstFile.getName());

        if (ext.equalsIgnoreCase("jpg")) {
            Compressor cmp = new Compressor();
            cmp.JpgCompressor(img, dstFile, Quality);
        } else {
            bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bf.setRGB(i, j, img.getPixels()[i + j * width]);
                }
            }
            try {
                ImageIO.write(bf, ext, dstFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
            bf.flush();
        }
    }

    /**
     * Read JPG format image and return a BufferedImage .
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }

}
