package com.ljc.review.common.image.watermark;

/**
 * Created by Rocky on 6/7/2017.
 */
public class Scaler {
    public ImageData ImgScaler (ImageData srcImg, int dstWidth, int dstHeight) {

        ImageData dstImg = new ImageData();
        if(dstWidth <= 0 || dstHeight <= 0){
            System.out.print("dstWidth or dstHeight must be positive!");
        }
        else{
            if(dstWidth == srcImg.getWidth() && dstHeight == srcImg.getHeight()){
                dstImg = srcImg;
            }
            else{
                if (dstWidth > srcImg.getWidth()) {
                    if ( dstHeight >= srcImg.getHeight()) {
                        UpScaling upScaleProcess = new UpScaling(srcImg);
                        dstImg = upScaleProcess.upscaling(dstWidth, dstHeight);
                    }
                    else {          //dstWidth > srcWidth && dstHeight < srcHeight
                        ImageData temp = new ImageData();
                        DownScaling downScalingPart = new DownScaling(srcImg);
                        temp = downScalingPart.downscaling(srcImg.getWidth(), dstHeight);
                        temp.setChannels();         //set ARGB channels by Pixels[]
                        UpScaling upScalingPart = new UpScaling(temp);
                        dstImg = upScalingPart.upscaling(dstWidth, dstHeight);
                    }
                }
                else{
                    if (dstHeight <= srcImg.getHeight()) {
                        DownScaling downScaleProcess = new DownScaling(srcImg);
                        dstImg = downScaleProcess.downscaling(dstWidth, dstHeight);
                    }
                    else {          //dstWidth < srcWidth && dstHeight > srcHeight
                        ImageData temp = new ImageData();
                        DownScaling downScalingPart = new DownScaling(srcImg);
                        temp = downScalingPart.downscaling(dstWidth, srcImg.getHeight());
                        temp.setChannels();         //set ARGB channels by Pixels[]
                        UpScaling upScalingPart = new UpScaling(temp);
                        dstImg = upScalingPart.upscaling(dstWidth, dstHeight);
                    }
                }
            }
        }

        return dstImg;

    }
}
