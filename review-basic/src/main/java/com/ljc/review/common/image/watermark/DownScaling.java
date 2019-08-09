package com.ljc.review.common.image.watermark;

/**
 * Created by Rocky on 5/27/2017.
 * This class is used to down scaling image
 */

public class DownScaling {

    private ImageData srcImg;

    public DownScaling (ImageData srcImg){
        this.srcImg = srcImg;
    }

    /**
     * The main part of this function is from the ColorProcessor.java of ImageJ(Vision: 1.51k).
     */

    public ImageData downscaling (int dstWidth, int dstHeight) {

        ImageData dstImg = new ImageData();
        dstImg.setSize(dstWidth, dstHeight);

        int[] dstPixel = new int[dstWidth*dstHeight];
        int channels = 4;
        for (int channelNumber = 0; channelNumber < channels; channelNumber++) {
            float[]  fpixel = toFloat(channelNumber);
            fpixel = downsize(dstWidth, dstHeight, fpixel);
            dstPixel = setPixels(channelNumber, fpixel, dstPixel, dstImg);
        }
        dstImg.setPixel(dstPixel);

        return dstImg;

    }

    /**
     * This function is from the ColorProcessor.java of ImageJ(Vision: 1.51k).
     */

    public float[] toFloat (int channelNumber) {

        int size = srcImg.getWidth() * srcImg.getHeight();
        float[] fPixels = new float[size];
        int shift = 24 - 8 * channelNumber;
        int byteMask = 255 << shift;
        for (int i = 0; i < size; i++)
            fPixels[i] = (float)((srcImg.getPixels()[i]&byteMask) >>> shift);
        return fPixels;

    }

    /**
     * This function is from the FloatProcessor.java of ImageJ(Vision: 1.51k).
     */

    public float[] downsize (int dstWidth, int dstHeight, float[] pixel) {

        pixel = downsize1D(dstWidth, srcImg.getHeight(), true, pixel, srcImg.getWidth());
        //downsizing in x
        pixel = downsize1D(dstWidth, dstHeight, false, pixel, dstWidth);
        //downsizing in y
        return pixel;

    }

    /**
     * This function is from the FloatProcessor.java of ImageJ(Vision: 1.51k).
     *
     * Downsizing in one direction.
     */

    public float[] downsize1D (int dstWidth, int dstHeight, boolean xDirection, float[] pixel, int width) {

        int srcPointInc = xDirection ? 1 : width;	//increment of array index for next point along direction
        int srcLineInc = xDirection ? width : 1;	//increment of array index for next line to be downscaled
        int dstPointInc = xDirection ? 1 : dstWidth;
        int dstLineInc = xDirection ? dstWidth : 1;
        int srcLine0 = 0;
        int dstLines = xDirection ? dstHeight : dstWidth;
        DownsizeTable dt = xDirection ?
                new DownsizeTable(srcImg.getWidth(), 0, srcImg.getWidth(), dstWidth) :
                new DownsizeTable(srcImg.getHeight(), 0, srcImg.getHeight(), dstHeight);
        float[] pixels = pixel;
        float[] dstPixels = new float[dstWidth * dstHeight];
        for (int srcLine = srcLine0, dstLine = 0; dstLine < dstLines; srcLine++,dstLine++) {
            int dstLineOffset = dstLine * dstLineInc;
            int tablePointer = 0;
            for (int srcPoint = dt.srcStart, p = srcPoint * srcPointInc + srcLine * srcLineInc; srcPoint <= dt.srcEnd; srcPoint++, p += srcPointInc) {
                float v = pixels[p];
                for (int i = 0; i < dt.kernelSize; i++, tablePointer++)
                    dstPixels[dstLineOffset+dt.indices[tablePointer] * dstPointInc] += v * dt.weights[tablePointer];
            }
        }
        return dstPixels;

    }

    /**
     * This function is from the ColorProcessor.java of ImageJ(Vision: 1.51k).
     */

    public static int[] setPixels(int channelNumber, float[] fpixel, int[] dstPixels, ImageData dstImg) {

        float value;
        int size = dstImg.getWidth() * dstImg.getHeight();
        int shift = 24 - 8 * channelNumber;
        int resetMask = 0xffffffff^(255 << shift);
        for (int i = 0; i < size; i++) {
            value = fpixel[i] + 0.5f;
            if (value<0f) value = 0f;
            if (value>255f) value = 255f;
            dstPixels[i] = (dstPixels[i]&resetMask) | ((int)value << shift);
        }
        return dstPixels;

    }

    /**
     * This function is from the ImageProcessor.java of ImageJ(Vision: 1.51k).
     */

    static final double a = 0.5; // Catmull-Rom interpolation
    public static final double cubic(double x) {
        if (x < 0.0) x = -x;
        double z = 0.0;
        if (x < 1.0)
            z = x * x * (x * (-a + 2.0) + (a - 3.0)) + 1.0;
        else if (x < 2.0)
            z = -a * x * x * x + 5.0 * a * x * x - 8.0 * a * x + 4.0 * a;
        return z;
    }

}
