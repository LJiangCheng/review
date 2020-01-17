package com.ljc.review.common.image.webp;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.regex.Pattern;

/**
 * Created by rocky on 6/23/17.
 */

public class Filter {
    private int dstWidth, dstHeight;
    private int Quality, QualityStart, QualityEnd, QualityStep;
    private double sigma, sigmaStart, sigmaEnd, sigmaStep;
    private double weight, weightStart, weightEnd, weightStep;
    private String Model, cmdType;
    private String srcF, dstF, refF, executeFile, dstFolder, resultPath;
    private String type;
    private File temp;

    public Filter(String model) {
        Model = model;
        dstWidth = -1;
        dstHeight = -1;
        Quality = 100;
        QualityStart = -1;
        QualityEnd = -1;
        QualityStep = -1;
        sigma = -1;
        sigmaStart = -1;
        sigmaEnd = -1;
        sigmaStep = -1;
        weight = -1;
        weightStart = -1;
        weightEnd = -1;
        weightStep = -1;
        srcF = null;
        dstF = null;
        refF = null;
        executeFile = null;
        dstFolder = null;
        resultPath = null;
        type = null;
        cmdType = null;
        temp = null;
    }

    public void filter(String[] args) {
        Pattern digit = Pattern.compile("^[-+]?\\d+(\\.\\d+)?$");
        int i = 2, flagO;

        while (i < args.length) {
            if (args[i].charAt(0) == '-') {
                cmdType = args[i];
                i++;
            }
            flagO = i;

            if (this.Model.equalsIgnoreCase("scale") || Model.equalsIgnoreCase("sharpen") || Model.equalsIgnoreCase("scalesharpen")
                    || this.Model.equalsIgnoreCase("compression")) {
                if (cmdType.equals("-size")) {
                    if (digit.matcher(args[i]).matches() && digit.matcher(args[i + 1]).matches()) {
                        this.dstWidth = Integer.parseInt(args[i]);
                        this.dstHeight = Integer.parseInt(args[i + 1]);
                        i += 2;
                    } else {
                        System.out.println("Width&Height invalid");
                    }

                }

                if (cmdType.equalsIgnoreCase("-sigma")) {
                    if (digit.matcher(args[i]).matches()) {
                        this.sigma = Double.parseDouble(args[i]);
                        i++;
                    } else {
                        System.out.println("Sigma invalid");
                    }
                }

                if (cmdType.equalsIgnoreCase("-weight")) {
                    if (digit.matcher(args[i]).matches()) {
                        this.weight = Double.parseDouble(args[i]);
                        i++;
                    } else {
                        System.out.println("Weight invalid");
                    }
                }

                if (cmdType.equalsIgnoreCase("-quality")) {
                    if (digit.matcher(args[i]).matches()) {
                        this.Quality = Integer.parseInt(args[i]);
                        i++;
                    } else {
                        System.out.println("Quality invalid");
                    }
                }
            }

            if (cmdType.equalsIgnoreCase("-src")) {
                this.srcF = args[i];
                i++;
            }

            if (cmdType.equalsIgnoreCase("-dst")) {
                this.dstF = args[i];
                temp = new File(dstF);
                if ((FilenameUtils.getExtension(temp.getName()).equals("jpg") || FilenameUtils.getExtension(temp.getName()).equals("webp"))
                        && (i + 1) < args.length && digit.matcher(args[i + 1]).matches()) {
                    this.Quality = Integer.parseInt(args[i + 1]);
                    i++;
                }
                i++;
            }

            if (cmdType.equals("-result")) {
                this.resultPath = args[i];
                i++;
            }

            if (Model.equalsIgnoreCase("batch_sharpen") || Model.equalsIgnoreCase("psnrsharpentest")
                    || Model.equalsIgnoreCase("batch_compress") || Model.equalsIgnoreCase("psnrcompresstest")) {
                if (cmdType.equals("-sigma")) {
                    if (digit.matcher(args[i]).matches() && digit.matcher(args[i + 1]).matches() && digit.matcher(args[i + 2]).matches()) {
                        this.sigmaStart = Double.parseDouble(args[i]);
                        this.sigmaEnd = Double.parseDouble(args[i + 1]);
                        this.sigmaStep = Double.parseDouble(args[i + 2]);
                        i += 3;
                    } else {
                        System.out.println("Sigma invalid in Scale&Sharpen process");
                    }
                }

                if (cmdType.equalsIgnoreCase("-weight")) {
                    if (digit.matcher(args[i]).matches() && digit.matcher(args[i + 1]).matches() && digit.matcher(args[i + 2]).matches()) {
                        this.weightStart = Double.parseDouble(args[i]);
                        this.weightEnd = Double.parseDouble(args[i + 1]);
                        this.weightStep = Double.parseDouble(args[i + 2]);
                        i += 3;
                    } else {
                        System.out.println("Weight invalid in Scale&Sharpen process");
                    }
                }

                if (cmdType.equalsIgnoreCase("-quality")) {
                    if (digit.matcher(args[i]).matches() && digit.matcher(args[i + 1]).matches() && digit.matcher(args[i + 2]).matches()) {
                        this.QualityStart = Integer.parseInt(args[i]);
                        this.QualityEnd = Integer.parseInt(args[i + 1]);
                        this.QualityStep = Integer.parseInt(args[i + 2]);
                        i += 3;
                    } else {
                        System.out.println("Quality invalid");
                    }
                }

                if (cmdType.equalsIgnoreCase("-o")) {
                    this.executeFile = args[i];
                    i++;
                }

                if (cmdType.equalsIgnoreCase("-dstfolder")) {
                    this.dstFolder = args[i];
                    i++;
                }

                if (cmdType.equalsIgnoreCase("-dstfile")) {
                    this.dstF = args[i];
                    temp = new File(dstF);
                    if ((FilenameUtils.getExtension(temp.getName()).equals("jpg") || FilenameUtils.getExtension(temp.getName()).equals("webp"))
                            && (i + 1) < args.length && digit.matcher(args[i + 1]).matches()) {
                        this.Quality = Integer.parseInt(args[i + 1]);
                        i++;
                    }
                    i++;
                }

                if (cmdType.equalsIgnoreCase("-dstfilename")) {
                    this.dstF = args[i];
                    i++;
                }

                if (cmdType.equalsIgnoreCase("-type")) {
                    if (args[i].equalsIgnoreCase("webp") || args[i].equalsIgnoreCase("jpg")
                            || args[i].equalsIgnoreCase("both")) {
                        this.type = args[i];
                    }
                    i++;
                }

                if (cmdType.equals("-ref")) {       //reference
                    this.refF = args[i];
                    i++;
                }
            }
            if (flagO == i) {
                System.out.println("Warning at: " + cmdType);
                i++;
            }
        }
    }

    public String getModel() {
        return Model;
    }

    public int getDstWidth() {
        return dstWidth;
    }

    public int getDstHeight() {
        return dstHeight;
    }

    public int getQuality() {
        return Quality;
    }

    public int getQualityStart() {
        return QualityStart;
    }

    public int getQualityEnd() {
        return QualityEnd;
    }

    public int getQualityStep() {
        return QualityStep;
    }

    public double getSigma() {
        return sigma;
    }

    public double getSigmaStart() {
        return sigmaStart;
    }

    public double getSigmaEnd() {
        return sigmaEnd;
    }

    public double getSigmaStep() {
        return sigmaStep;
    }

    public double getWeight() {
        return weight;
    }

    public double getWeightStart() {
        return weightStart;
    }

    public double getWeightEnd() {
        return weightEnd;
    }

    public double getWeightStep() {
        return weightStep;
    }

    public String getSrcF() {
        return srcF;
    }

    public String getDstF() {
        return dstF;
    }

    public String getRefF() {
        return refF;
    }

    public String getDstFolder() {
        return dstFolder;
    }

    public String getResultPath() {
        return resultPath;
    }

    public String getExecuteFile() {
        return executeFile;
    }

    public String getType() {
        return type;
    }

}
