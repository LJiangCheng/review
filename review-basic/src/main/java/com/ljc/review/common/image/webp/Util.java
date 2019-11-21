package com.ljc.review.common.image.webp;

import com.luciad.imageio.webp.WebPReadParam;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.System.exit;

/**
 * Created by Rocky on 6/7/2017.
 */
public class Util {

    public void printHelp(){
        System.out.print("***** Command line syntax *****\r\n\n"
                + "Image Scale: java -jar ImageEditor.jar -a scale -size [dstWidth] [dstHeight] \r\n"
                + "                           -src [\"source image path\"] -dst [\"destination image path\"]\r\n\n"
                + "Image Sharpen: java -jar ImageEditor.jar -a sharpen -sigma [sigma] -weight [weight] \r\n"
                + "                           -src [\"source image path\"] -dst [\"destination image path\"]\r\n\n"
                + "Image Compression: java -jar ImageEditor.jar -a compression -quality [compress quality] \r\n"
                + "                           -src [\"source image path\"] -dst [\"destination image path\"]\r\n\n"
                + "Image Scale and Sharpen: java -jar ImageEditor.jar -a scale&sharpen -size [dstWidth] [dstHeight]\r\n"
                + "                           -sigma [sigma] -weight [weight] -src [\"source image path\"] \r\n"
                + "                           -dst [\"destination image path\"]\r\n\n"
                + "java -jar ImageEditor.jar -a psnr -src [\"source image path\"] -dst [\"destination image path\"]\r\n\n"
                + "Batch Sharpening image with PSNR test: java -jar ImageEditor.jar -a batch_sharpen \r\n"
                + "                           -sigma [sigmaStart] [sigmaEnd] [sigmaEnd] -weight [weightStart] [weightEnd] [weightEnd]\r\n"
                + "                           -o [\"batch file name\"(*.bat in Windows or *.sh in Linux)] -src [\"source image path\"]\r\n"
                + "                           -dstfolder [\"destination folder path\"] -dstfile [\"destination image name\"] \r\n"
                + "                           -ref [\"reference image path in the PSNR test\"] -result [\"PSNR test result path\"]\r\n\n"
                + "Batch Compressing image with PSNR test: java -jar ImageEditor.jar -a batch_compress \r\n"
                + "                           -quality [qualityStart] [qualityEnd] [qualityEnd] -o [\"batch file name\"(*.bat in Windows or *.sh in Linux)] \r\n"
                + "                           -src [\"source image path\"] -dstfolder [\"destination folder path\"] \r\n"
                + "                           -dstfilename [\"destination image name without image type\"] -result [\"PSNR test result path\"]\r\n"
                + "                           -type[\"destination image type\"(\"jpg\", \"webp\" or \"both\", \"both\" will output both two types image)]\r\n");
    }

    /**
     * Read the source image to Object srcImg
     */

    public ImageData readImage(File srcFile) {

        ImageData srcImg = new ImageData();
        int[] pixel = null;

        if (!srcFile.exists()) {
            System.out.println("The Read File Not Exist!");
            exit(1);
        }
        try {
            BufferedImage bufImg = null;
            if(FilenameUtils.getExtension(srcFile.getName()).equalsIgnoreCase("jpg")){
                Image src = new ImageIcon(srcFile.getPath()).getImage();
                bufImg = toBufferedImage(src);//Image to BufferedImage
            }
            else{
                if (FilenameUtils.getExtension(srcFile.getName()).equalsIgnoreCase("webp")) {
                    bufImg = webpDecoder(srcFile);
                }
                else{
                    bufImg = ImageIO.read(srcFile);
                }
            }

            int height = bufImg.getHeight();
            int width = bufImg.getWidth();
            srcImg.setSize(width,height);
            pixel = new int[width*height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    pixel[i+j*width] = bufImg.getRGB(i, j) & 0xFFFFFFFF;
                }
            }
            srcImg.setPixel(pixel);

            int c, a, r, g, b;
            byte[] A = new byte[width*height];
            byte[] R = new byte[width*height];
            byte[] G = new byte[width*height];
            byte[] B = new byte[width*height];
            for (int i = 0; i < width*height;i++){
                c = pixel[i];
                a = (c&0xff000000)>> 24;
                r = (c&0xff0000)>>16;
                g = (c&0xff00)>>8;
                b = c&0xff;
                A[i] = (byte)a;
                R[i] = (byte)r;
                G[i] = (byte)g;
                B[i] = (byte)b;
            }
            srcImg.setChannels(A, R, G, B);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return srcImg;
    }

    /**
     * Write the destination image to file.
     */

    public void writeImage(ImageData Img, File dstFile, int Quality){

        int width = Img.getWidth();
        int height = Img.getHeight();
        BufferedImage bf = null;
        String ext = FilenameUtils.getExtension(dstFile.getName());

        if (ext.equalsIgnoreCase("jpg")) {
            Compressor cmp = new Compressor();
            cmp.JpgCompressor(Img, dstFile, Quality);
        }
        else{
            if (ext.equalsIgnoreCase("webp")) {
                Compressor cmp = new Compressor();
                cmp.WebpCompressor(Img, dstFile, Quality);

            }
            else{
                bf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                for (int i = 0; i < width; i++) {
                    for (int j = 0; j < height; j++) {
                        bf.setRGB(i, j, Img.getPixels()[i + j * width]);
                    }
                }
                try {
                    File file = dstFile;
                    ImageIO.write(bf, ext, file);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Read JPG format image and return a BufferedImage .
     */

    public static BufferedImage toBufferedImage(Image img)
    {
        if (img instanceof BufferedImage)
        {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static BufferedImage webpDecoder(File srcFile){
        // Obtain a WebP ImageReader instance
        BufferedImage image = null;
        try{
            ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
            // Configure decoding parameters
            WebPReadParam readParam = new WebPReadParam();
            readParam.setBypassFiltering(true);
            // Configure the input on the ImageReader
            reader.setInput(new FileImageInputStream(srcFile));
            // Decode the image
            image = reader.read(0, readParam);
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public String printNewline(){
        if (System.getProperty("os.name").toLowerCase().startsWith("win")) {
            return "\r\n";
        } else {
            return "\n";
        }
    }

    public String printSlash(){
        if(System.getProperty("os.name").toLowerCase().startsWith("win")){
            return "\\";
        }
        else{
            return "/";
        }
    }

}
