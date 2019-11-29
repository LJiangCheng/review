package com.ljc.review.common.image.watermark;

import org.apache.commons.io.FilenameUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rocky on 6/5/2017.
 */

public class Compressor {

    public void JpgCompressor (ImageData img, File dstImgDir, int Quality) {
        ImageOutputStream imageOutputStream = null;
        try {
            int width = img.getWidth();
            int height = img.getHeight();
            BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bf.setRGB(i, j, img.getPixels()[i + j * width]);
                }
            }
            //Compress image
            ImageWriter imageWriter;
            imageOutputStream = ImageIO.createImageOutputStream(dstImgDir);
            imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(dstImgDir.getName())).next();
            imageWriter.setOutput(imageOutputStream);
            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality((float)Quality / (float)100.0);
            imageWriter.write(null, new IIOImage(bf, null, null), imageWriteParam);
            imageOutputStream.flush();
            bf.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if (imageOutputStream != null) {
                try {
                    imageOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
