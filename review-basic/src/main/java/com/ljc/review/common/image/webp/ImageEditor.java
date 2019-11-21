package com.ljc.review.common.image.webp;

import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import static java.lang.System.exit;

/**
 * Created by Rocky on 5/24/2017.
 */

public class ImageEditor {
    public static void main(String[]args){

        Util util = new Util();

        if (args.length < 1){
            util.printHelp();
        }

        try {
            String Model = null;

            if (args[0].equalsIgnoreCase("-a")) {
                Model = args[1].toLowerCase();
            } else {
                util.printHelp();
                exit(1);
            }

            if (!(Model.equalsIgnoreCase("scale") || Model.equalsIgnoreCase("sharpen")
                    || Model.equalsIgnoreCase("compression") || Model.equalsIgnoreCase("scalesharpen")
                    || Model.equalsIgnoreCase("psnr") || Model.equalsIgnoreCase("batch_sharpen")
                    || Model.equalsIgnoreCase("batch_compress") || Model.equalsIgnoreCase("psnrsharpentest")
                    || Model.equalsIgnoreCase("psnrcompresstest")) || Model.equalsIgnoreCase("help")) {
                util.printHelp();
                exit(1);
            }

            Filter f = new Filter(Model);
            f.filter(args);

            Branching(Model, f);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Branching(String Model, Filter f){

        try{
            Util util = new Util();
            DecimalFormat df   = new DecimalFormat("######0.00");
            File srcFile = null, dstFile = null, batF = null;
            ImageData srcImg = null, dstImg = null;
            Scaler is =null;
            Sharpen sharpen = null;

            if (f.getSrcF() != null){
                srcFile = new File(f.getSrcF());
                if(!srcFile.exists()){
                    System.out.println("Source file dose not exist!");
                }
            }

            switch (Model){
                case "scale":             //Scaling
                    if(f.getDstWidth() != -1 && f.getDstHeight() != -1 && f.getSrcF() != null && f.getDstF() != null){
                        dstFile = new File(f.getDstF());
                        is = new Scaler();
                        dstImg = is.ImgScaler(srcFile, dstFile, f.getDstWidth(), f.getDstHeight());
                        util.writeImage(dstImg, dstFile, f.getQuality());
                    }
                    else{
                        System.out.println("Parameter missing in Scale!");
                    }
                    break;

                case "sharpen":             //Sharpen
                    if(f.getSigma() != -1 && f.getWeight() != -1 && f.getSrcF() != null && f.getDstF() != null){
                        dstFile = new File(f.getDstF());
                        sharpen = new Sharpen();
                        dstImg = sharpen.ImageSharpen(srcFile, f.getSigma(), (float) f.getWeight());
                        util.writeImage(dstImg, dstFile, f.getQuality());
                    }
                    else{
                        System.out.println("Parameter missing in Sharpen!");
                    }

                    break;

                case "compression":             //Compression
                    if(f.getSrcF() != null && f.getDstF() != null){
                        dstFile = new File(f.getDstF());
                        srcImg = util.readImage(srcFile);
                        if(FilenameUtils.getExtension(dstFile.getName()).equals("jpg") || FilenameUtils.getExtension(dstFile.getName()).equals("webp")){
                            util.writeImage(srcImg, dstFile, f.getQuality());
                        }
                    }else{
                        System.out.println("Parameter missing in Compression!");
                    }
                    break;

                case "scalesharpen":             //Scaling+Sharpen
                    if(f.getDstWidth() != -1 && f.getDstHeight() != -1 && f.getSigma() != -1 && f.getWeight() != -1
                            && f.getSrcF() != null && f.getDstF() != null){
                        dstFile = new File(f.getDstF());
                        is = new Scaler();
                        sharpen = new Sharpen();
                        dstImg = is.ImgScaler(srcFile, dstFile, f.getDstWidth(), f.getDstHeight());
                        dstImg = sharpen.ImageSharpen(dstImg, f.getSigma(), (float) f.getWeight());
                        util.writeImage(dstImg, dstFile, f.getQuality());
                    }
                    else{
                        System.out.println("Parameter missing in S+S!");
                    }
                    break;

                case "psnr":             //PSNR test
                    if(f.getSrcF() != null && f.getDstF() != null){
                        dstFile = new File(f.getDstF());
                        if (dstFile ==null){
                            System.out.println("Second file does not exist in PSNR test!");
                            exit(1);
                        }
                        srcImg = util.readImage(srcFile);
                        dstImg = util.readImage(dstFile);
                        psnrTest psnr = new psnrTest();
                        psnr.PSNR(srcImg, dstImg);
                    }
                    else{
                        System.out.println("Parameter missing in PSNR Test!");
                    }

                    break;

                case "batch_sharpen":
                    if(f.getExecuteFile() != null && f.getSrcF() != null && f.getDstFolder() != null && f.getDstF() != null
                            && f.getSigmaStart() != -1 && f.getSigmaEnd() != -1 && f.getSigmaStep() != -1
                            && f.getWeightStart() != -1 && f.getWeightEnd() != -1 && f.getWeightStep() != -1){
                        ArrayList<String> CmdSharpen= new ArrayList<>();
                        for (double s = f.getSigmaStart(); s <= f.getSigmaEnd(); s += f.getSigmaStep()){
                            for (double w =  f.getWeightStart(); w <= f.getWeightEnd(); w = Math.round((w + f.getWeightStep())*100)/(double)100){
                                StringBuffer sb = new StringBuffer();
                                sb.append("java -jar ImageEditor.jar -a sharpen -sigma ").append(s).append(" -weight ").append(w);
                                sb.append(" -src ").append("\"").append(f.getSrcF()).append("\"");
                                sb.append(" -dst ").append("\"").append(f.getDstFolder()).append(util.printSlash());
                                sb.append(s).append("x").append(df.format(w)).append("_").append(f.getDstF()).append("\"");
                                if (f.getQuality() != 100){
                                    sb.append(" ").append(f.getQuality());
                                }
                                CmdSharpen.add(sb.append(util.printNewline()).toString());
                            }
                        }

                        try{
                            batF = new File(f.getExecuteFile());
                            FileOutputStream fos = new FileOutputStream(batF);
                            OutputStreamWriter osw = new OutputStreamWriter(fos);
                            Writer w = new BufferedWriter(osw);
                            for (int j = 0; j < CmdSharpen.size(); j++){
                                w.write(CmdSharpen.get(j));
                            }
                            if (f.getRefF() != null){
                                StringBuffer sb = new StringBuffer();
                                sb.append("java -jar ImageEditor.jar -a psnrSharpenTest -sigma ");
                                sb.append(f.getSigmaStart()).append(" ").append(f.getSigmaEnd()).append(" ").append(f.getSigmaStep());
                                sb.append(" -weight ").append(df.format(f.getWeightStart())).append(" ");
                                sb.append(df.format(f.getWeightEnd())).append(" ").append(df.format(f.getWeightStep()));
                                sb.append(" -dstfolder ").append("\"" + f.getDstFolder()).append("\"");
                                sb.append(" -dstfile ").append("\"" + f.getDstF() + "\"").append(" -ref ").append("\"" + f.getRefF() + "\"");
                                sb.append(" -result ").append("\"" + f.getResultPath() + "\"");
                                w.write(sb.toString());
                            }
                            w.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println("Parameter missing in batch_sharpen!");
                    }
                    break;

                case "batch_compress":
                    if (f.getExecuteFile() != null && f.getSrcF() != null && f.getDstFolder() != null && f.getDstF() != null
                            && f.getQualityStart() != -1 && f.getQualityEnd() != -1 && f.getQualityStep() != -1
                            && f.getType() != null && f.getResultPath() != null ) {
                        ArrayList<String> CmdCompress= new ArrayList<>();
                        if (f.getType().equalsIgnoreCase("both")) {
                            for (int q = f.getQualityStart(); q <= f.getQualityEnd(); q += f.getQualityStep()) {
                                StringBuffer sb = new StringBuffer();
                                sb.append("java -jar ImageEditor.jar -a compression -src ")
                                        .append("\"" + f.getSrcF() + "\"").append(" -dst ").append("\"" + f.getDstFolder())
                                        .append(util.printSlash())
                                        .append(f.getDstF() + "_" + df.format((double)q/(double)100) + ".webp\"").append(" -quality ").append(q);
                                CmdCompress.add(sb.append(util.printNewline()).toString());
                            }
                            for (int q = f.getQualityStart(); q <= f.getQualityEnd(); q += f.getQualityStep()) {
                                StringBuffer sb = new StringBuffer();
                                sb.append("java -jar ImageEditor.jar -a compression -src ")
                                        .append("\"" + f.getSrcF() + "\"").append(" -dst ").append("\"" + f.getDstFolder())
                                        .append(util.printSlash())
                                        .append(f.getDstF() + "_" + df.format((double)q/(double)100) + ".jpg\"").append(" -quality ").append(q);
                                CmdCompress.add(sb.append(util.printNewline()).toString());
                            }
                        }
                        else{
                            for (int q = f.getQualityStart(); q <= f.getQualityEnd(); q += f.getQualityStep()) {
                                StringBuffer sb = new StringBuffer();
                                sb.append("java -jar ImageEditor.jar -a compression -src ")
                                        .append("\"" + f.getSrcF() + "\"").append(" -dst ").append("\"" + f.getDstFolder())
                                        .append(util.printSlash())
                                        .append(f.getDstF() + "_" + df.format((double)q/(double)100) + "." + f.getType() + "\"").append(" -quality ").append(q);
                                CmdCompress.add(sb.append(util.printNewline()).toString());
                            }
                        }

                        try{
                            batF = new File(f.getExecuteFile());
                            FileOutputStream fos = new FileOutputStream(batF);
                            OutputStreamWriter osw = new OutputStreamWriter(fos);
                            Writer w = new BufferedWriter(osw);
                            for (int j = 0; j < CmdCompress.size(); j++){
                                w.write(CmdCompress.get(j));
                            }
                            StringBuffer sb = new StringBuffer();
                            sb.append("java -jar ImageEditor.jar -a psnrcompressTest -Quality ")
                                    .append(f.getQualityStart() + " " + f.getQualityEnd() + " " + f.getQualityStep())
                                    .append(" -dstfolder ").append("\"" + f.getDstFolder()).append("\"")
                                    .append(" -dstfilename ").append("\"" + f.getDstF() + "\"")
                                    .append(" -type ").append(f.getType())
                                    .append(" -ref ").append("\"" + f.getSrcF() + "\"")
                                    .append(" -result ").append("\"" + f.getResultPath() + "\"");
                            w.write(sb.toString());
                            w.close();
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else{
                        System.out.println("Parameter missing in batch_compress!");
                    }
                    break;

                case "psnrsharpentest":
                    if(f.getSigmaStart() != -1 && f.getSigmaEnd() != -1 && f.getSigmaStep() != -1 && f.getWeightStart() != -1
                            && f.getWeightEnd() != -1 && f.getWeightStep() != -1 && f.getRefF() != null && f.getDstFolder() != null
                            && f.getDstF() != null && f.getResultPath() != null){
                        int col = (int)Math.round((f.getWeightEnd() - f.getWeightStart()) / f.getWeightStep()) + 1;
                        int row = (int)((f.getSigmaEnd() - f.getSigmaStart()) / f.getSigmaStep()) + 1;
                        System.out.println(col + " " + row);
                        double [][] psnrTable = new double[row + 1][col + 1];
                        for(int i = 1; i <= row ; i++){
                            psnrTable[i][0] = f.getSigmaStart() + (i - 1) * f.getSigmaStep();
                        }
                        for(int i = 1; i <= col; i++){
                            psnrTable[0][i] = Math.round((f.getWeightStart() * 100 + (i - 1) * f.getWeightStep() * 100)) / (double)100;
                        }
                        psnrTest psnrT = new psnrTest();
                        psnrT.psnrSharpen(psnrTable, f.getDstFolder(), f.getDstF(), f.getRefF(), f.getResultPath());
                    }
                    else{
                        System.out.println("Parameter missing in Sharpen PSNR test!");
                    }

                    break;

                    case "psnrcompresstest":
                        if (f.getRefF() != null && f.getDstFolder() != null && f.getDstF() != null && f.getType() != null && f.getResultPath() != null
                                && f.getQualityStart() != -1 && f.getQualityEnd() != -1 && f.getQualityStep() != -1) {
                            int col;
                            if(f.getType().equalsIgnoreCase("both")) {
                                col = 4;
                            }
                            else{
                                col = 2;
                            }
                            int row = ((f.getQualityEnd() - f.getQualityStart()) / f.getQualityStep()) + 1;
                            System.out.println(col+" "+row);
                            double [][] psnrTable = new double[row + 1][col + 1];
                            for(int i = 1; i <= row ; i++){
                                psnrTable[i][0] = f.getQualityStart() + (i - 1) * f.getQualityStep();
                            }

                            psnrTest psnrT = new psnrTest();
                            psnrT.psnrCompress(psnrTable, f.getDstFolder(), f.getDstF(), f.getRefF(), f.getType(), f.getResultPath());


                        }
                        else{
                            System.out.println("Parameter missing in Compress PSNR test!");
                        }

                        break;

                case "help":
                    util.printHelp();
                    break;
                default:
                    break;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}