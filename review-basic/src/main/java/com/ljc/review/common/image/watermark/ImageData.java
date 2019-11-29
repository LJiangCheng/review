package com.ljc.review.common.image.watermark;

/**
 * Created by Rocky on 5/24/2017.
 * This class contains the info of image
 */

public class ImageData {

    private int width;
    private int height;
    private int[] Pixels;

    public ImageData(){
        width = 0;
        height = 0;
    }

    public void setPixel(int[] pixel){
        Pixels = pixel;
    }

    public void setSize(int w, int h){
        width = w;
        height = h;
    }

    public int getWidth(){
        return width;
    }

    public int getHeight(){
        return height;
    }

    public int[] getPixels(){
        return Pixels;
    }

}