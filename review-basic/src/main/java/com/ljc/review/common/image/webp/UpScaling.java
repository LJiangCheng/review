package com.ljc.review.common.image.webp;

/**
 * Created by Rocky on 5/27/2017.
 * This class is used to up scaling image
 */

public class UpScaling {

    private ImageData srcImg;

    public UpScaling (ImageData SrcImg){
        this.srcImg = SrcImg;
    }

    /**
     * The main part of this function is from the ColorProcessor.java of ImageJ(Vision: 1.51k).
     */

    public ImageData upscaling(int dstWidth, int dstHeight ){

        ImageData dstImg = new ImageData();
        dstImg.setSize(dstWidth, dstHeight);

        //Below four lines is from case RGB_RESIZE
        byte [] a = byteProcess(dstWidth, dstHeight, srcImg.getAchannel());
        byte [] r = byteProcess(dstWidth, dstHeight, srcImg.getRchannel());
        byte [] g = byteProcess(dstWidth, dstHeight, srcImg.getGchannel());
        byte [] b = byteProcess(dstWidth, dstHeight, srcImg.getBchannel());
        srcImg.setChannels(a, r, g, b);

        int [] pixel = new int[dstWidth*dstHeight];
        for (int i=0; i < dstWidth*dstHeight; i++){
            pixel[i] = ((a[i]&0xff)<<24) | ((r[i]&0xff)<<16) | ((g[i]&0xff)<<8) | b[i]&0xff;
        }
        dstImg.setPixel(pixel);

        return dstImg;
    }

    /**
     * This function is from the ByteProcessor.java of ImageJ(Vision: 1.51k)
     * and the function name in ByteProcessor.java is resize(int dstWidth, int dstHeight).
     */

    public byte[] byteProcess( int dstWidth, int dstHeight, byte[] srcPixel){

        int width = srcImg.getWidth(), height = srcImg.getHeight();
        double srcCenterX = width/2.0;
        double srcCenterY = height/2.0;
        double dstCenterX = dstWidth/2.0;
        double dstCenterY = dstHeight/2.0;
        double xScale = (double)dstWidth/width;
        double yScale = (double)dstHeight/height;

        if (dstWidth!= width) dstCenterX+= xScale/4.0;
        if (dstHeight!= height) dstCenterY+= yScale/4.0;

        byte[] dstPixel = new byte[dstWidth*dstHeight];
        double xs, ys;
        int index;
        for (int y=0; y<=dstHeight-1; y++) {
            ys = (y-dstCenterY)/yScale + srcCenterY;;
            index = y*dstWidth;
            for (int x=0; x<=dstWidth-1; x++) {
                xs = (x-dstCenterX)/xScale + srcCenterX;
                int value = (int)(getBicubicInterpolatedPixel(xs, ys, srcPixel)+0.5);
                if (value<0) value = 0;
                if (value>255) value = 255;
                dstPixel[index++] = (byte)value;
            }
        }
        return dstPixel;

    }

    /**
     * This function is from the ImageProcessor.java of ImageJ(Vision: 1.51k).
     *
     * This method is from Chapter 16 of "Digital Image Processing:
     * An Algorithmic Introduction Using Java" by Burger and Burge
     * http://www.imagingbook.com/).
     */

    public double getBicubicInterpolatedPixel(double x0, double y0, byte [] pixels) {

        int width = srcImg.getWidth(), height = srcImg.getHeight();

        int u0 = (int) Math.floor(x0);	//use floor to handle negative coordinates too
        int v0 = (int) Math.floor(y0);
        if (u0<=0 || u0>= width-2 || v0<=0 || v0 >= height-2)
            return getInterpolatedPixel(x0, y0, pixels);
        double q = 0;
        for (int j = 0; j <= 3; j++) {
            int v = v0 - 1 + j;
            double p = 0;
            for (int i = 0; i <= 3; i++) {
                int u = u0 - 1 + i;
                p = p + get(u, v, pixels) * cubic(x0 - u);
            }
            q = q + p * cubic(y0 - v);
        }
        return q;
    }

    /**
     * This function is from the ByteProcessor.java of ImageJ(Vision: 1.51k).
     */

    public final int get(int x, int y, byte[] pixels){
        return pixels[y* srcImg.getWidth()+x]&0xff;
    }

    /**
     * This function is from the ByteProcessor.java of ImageJ(Vision: 1.51k).
     *
     * Uses the BiLinear method to calculate the pixel value at real coordinates (x,y).
     * */

    public double getInterpolatedPixel(double x, double y, byte[] pixels) {

        int width = srcImg.getWidth(), height = srcImg.getHeight();

        //The below part is in the getInterpolatedPixel(double x, double y) function
        if (x<0.0) x = 0.0;
        if (x>=width-1.0) x = width-1.001;
        if (y<0.0) y = 0.0;
        if (y>=height-1.0) y = height-1.001;

        //The below part is in the getInterpolatedPixel(double x, double y, byte[] pixels) function
        int xbase = (int)x;
        int ybase = (int)y;
        double xFraction = x - xbase;
        double yFraction = y - ybase;
        int offset = ybase * width + xbase;
        int lowerLeft = pixels[offset]&255;
        int lowerRight = pixels[offset + 1]&255;
        int upperRight = pixels[offset + width + 1]&255;
        int upperLeft = pixels[offset + width]&255;
        double upperAverage = upperLeft + xFraction * (upperRight - upperLeft);
        double lowerAverage = lowerLeft + xFraction * (lowerRight - lowerLeft);
        return lowerAverage + yFraction * (upperAverage - lowerAverage);
    }

    /**
     * This function is from the ImageProcessor.java of ImageJ(Vision: 1.51k).
     */

    static final double a = 0.5; // Catmull-Rom interpolation
    public static final double cubic(double x) {
        if (x < 0.0) x = -x;
        double z = 0.0;
        if (x < 1.0)
            z = x*x*(x*(-a+2.0) + (a-3.0)) + 1.0;
        else if (x < 2.0)
            z = -a*x*x*x + 5.0*a*x*x - 8.0*a*x + 4.0*a;
        return z;
    }

}
