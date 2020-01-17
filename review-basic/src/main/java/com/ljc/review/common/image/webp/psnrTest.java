package com.ljc.review.common.image.webp;

import java.io.*;
import java.text.DecimalFormat;

import static java.lang.System.exit;

/**
 * Created by Rocky on 6/7/2017.
 */

public class psnrTest {

    public double PSNR(ImageData dstImg, ImageData ref) {
        Util util = new Util();
        double[] RGB = new double[3];
        DecimalFormat df = new DecimalFormat("####0.0000");
        for (int k = 0; k < 3; k++) {
            byte[] oriP = null, dstP = null;
            if (k == 0) {
                oriP = dstImg.getRchannel();
                dstP = ref.getRchannel();
            }
            if (k == 1) {
                oriP = dstImg.getGchannel();
                dstP = ref.getGchannel();
            }
            if (k == 2) {
                oriP = dstImg.getBchannel();
                dstP = ref.getBchannel();
            }
            double sum = 0;
            for (int j = 0; j < ref.getWidth() * ref.getHeight(); j++) {
                sum = sum + Math.pow((int) oriP[j] - (int) dstP[j], 2);
            }

            double PSNR = 10 * Math.log10(255.0 * 255.0 * (double) ref.getWidth() * (double) ref.getHeight() / sum);
            RGB[k] = PSNR;
        }
        double sumPSNR = RGB[0] + RGB[1] + RGB[2];
        System.out.println("  PSNR = " + df.format(sumPSNR) + "\tR: " + df.format(RGB[0]) + "\tG: " + df.format(RGB[1]) + "\tB: " + df.format(RGB[2]) + util.printSlash());
        return sumPSNR;

    }

    public void psnrSharpen(double[][] psnrTable, String dstFolder, String dstFName, String refF, String resultDir) {
        Util util = new Util();
        DecimalFormat df = new DecimalFormat("######0.00");
        int row = psnrTable.length, col = psnrTable[0].length;
        ImageData refImg = null;
        double sigma, weight;
        String dstF;
        File refImage = null, dstFile = null, psnrF;
        refImage = new File(refF);
        if (refImage == null) {
            System.out.println("Reference image does not exist!");
            exit(1);
        } else {
            refImg = util.readImage(refImage);
        }
        for (int i = 1; i < row; i++) {
            for (int j = 1; j < col; j++) {
                sigma = psnrTable[i][0];
                weight = psnrTable[0][j];
                System.out.println("sigma: " + sigma + " weight: " + df.format(weight));
                StringBuffer sb = new StringBuffer();
                sb.append(dstFolder).append(util.printSlash());
                sb.append(sigma).append("x").append(df.format(weight)).append("_").append(dstFName);
                dstF = sb.toString();
                dstFile = new File(dstF);
                if (dstFile == null) {
                    System.out.println(sigma + " " + weight);
                } else {
                    ImageData dstImg = util.readImage(dstFile);
                    psnrTable[i][j] = PSNR(dstImg, refImg);
                }
            }
        }

        StringBuffer sb = new StringBuffer();
        sb.append("s\\w,");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (i != 0 || j != 0) {
                    sb.append(df.format(psnrTable[i][j])).append(",");
                }
            }
            sb.append(util.printNewline());
        }

        try {
            psnrF = new File(resultDir);
            FileOutputStream fos = new FileOutputStream(psnrF);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Writer w = new BufferedWriter(osw);
            w.write(sb.toString());
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void psnrCompress(double[][] psnrTable, String dstFolder, String dstFName, String refF, String type, String resultDir) {
        Util util = new Util();
        DecimalFormat df1 = new DecimalFormat("######0.0");
        DecimalFormat df2 = new DecimalFormat("#####0.00");
        int row = psnrTable.length, col = psnrTable[0].length;
        ImageData refImg = null;
        int quality;
        String dstF;
        File refImage = null, dstFile = null, psnrF;
        refImage = new File(refF);
        if (refImage == null) {
            System.out.println("Reference image does not exist!");
            exit(1);
        } else {
            refImg = util.readImage(refImage);
        }
        if (col == 3) {
            for (int i = 1; i < row; i++) {
                quality = (int) psnrTable[i][0];
                System.out.println("quality: " + quality);
                StringBuffer sb = new StringBuffer();
                sb.append(dstFolder).append(util.printSlash());
                sb.append(dstFName + "_" + df2.format((double) quality / (double) 100) + "." + type);
                dstF = sb.toString();
                dstFile = new File(dstF);
                if (dstFile == null) {
                    System.out.println(quality);
                } else {
                    ImageData dstImg = util.readImage(dstFile);
                    psnrTable[i][1] = PSNR(dstImg, refImg);
                }
            }
        } else {
            for (int i = 1; i < row; i++) {
                quality = (int) psnrTable[i][0];
                System.out.println("quality: " + quality);
                StringBuffer sb = new StringBuffer();
                sb.append(dstFolder).append(util.printSlash());
                sb.append(dstFName + "_" + df2.format((double) quality / (double) 100) + ".webp");
                dstF = sb.toString();
                dstFile = new File(dstF);
                if (dstFile == null) {
                    System.out.println(quality);
                } else {
                    ImageData dstImg = util.readImage(dstFile);
                    psnrTable[i][1] = PSNR(dstImg, refImg);
                    psnrTable[i][2] = dstFile.length();
                }
            }
            for (int i = 1; i < row; i++) {
                quality = (int) psnrTable[i][0];
                System.out.println("quality: " + quality);
                StringBuffer sb = new StringBuffer();
                sb.append(dstFolder).append(util.printSlash());
                sb.append(dstFName + "_" + df2.format((double) quality / (double) 100) + ".jpg");
                dstF = sb.toString();
                dstFile = new File(dstF);
                if (dstFile == null) {
                    System.out.println(quality);
                } else {
                    ImageData dstImg = util.readImage(dstFile);
                    psnrTable[i][3] = PSNR(dstImg, refImg);
                    psnrTable[i][4] = dstFile.length();
                }
            }
        }


        StringBuffer sb = new StringBuffer();
        if (col == 3) {
            sb.append("Quality," + type + "_psnr,size,").append(util.printNewline());
            for (int i = 1; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    sb.append(df1.format(psnrTable[i][j])).append(",");
                }
                sb.append(util.printNewline());
            }
        } else {
            sb.append("Quality," + "webp_psnr,size,jpg_psnr,size").append(util.printNewline());
            for (int i = 1; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    sb.append(df1.format(psnrTable[i][j])).append(",");
                }
                sb.append(util.printNewline());
            }
        }

        try {
            psnrF = new File(resultDir);
            FileOutputStream fos = new FileOutputStream(psnrF);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            Writer w = new BufferedWriter(osw);
            w.write(sb.toString());
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
