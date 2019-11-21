package com.ljc.review.common.image.webp;

import com.luciad.imageio.webp.WebPWriteParam;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Rocky on 6/5/2017.
 */

public class Compressor {

    public void JpgCompressor (ImageData Img, File dstImgDir, int Quality) {

        try {

            int width = Img.getWidth();
            int height = Img.getHeight();
            BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bf.setRGB(i, j, Img.getPixels()[i + j * width]);
                }
            }
            File file = dstImgDir;
            //Compress image
            ImageOutputStream imageOutputStream = null;
            ImageWriter imageWriter = null;
            imageOutputStream = ImageIO.createImageOutputStream(file);
            imageWriter = ImageIO.getImageWritersByFormatName(FilenameUtils.getExtension(dstImgDir.getName())).next();
            imageWriter.setOutput(imageOutputStream);
            ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
            imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            imageWriteParam.setCompressionQuality((float)Quality / (float)100.0);
            imageWriter.write(null, new IIOImage(bf, null, null), imageWriteParam);
            imageOutputStream.flush();
            imageOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void WebpCompressor (ImageData Img, File dstImgDir, int Quality) {

        try {

            int width = Img.getWidth();
            int height = Img.getHeight();
            BufferedImage bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    bf.setRGB(i, j, Img.getPixels()[i + j * width]);
                }
            }
            File file = dstImgDir;
            //Compress image
            ImageWriter writer = ImageIO.getImageWritersByMIMEType("image/webp").next();
            WebPWriteParam writeParam = new WebPWriteParam(writer.getLocale());
            writeParam.setCompressionMode(WebPWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionType("Lossy");                     //"Lossy", "Lossless"
            writeParam.setCompressionQuality((float)Quality / (float)100);
            writer.setOutput(new FileImageOutputStream(file));
            writer.write(null, new IIOImage(bf, null, null), writeParam);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
