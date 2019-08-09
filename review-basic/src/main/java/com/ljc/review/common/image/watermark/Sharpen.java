package com.ljc.review.common.image.watermark;

/**
 * Created by Rocky on 6/6/2017.
 * This class is used to sharpen image by Unsharp Mask approach
 */

public class Sharpen {

    /**
     * The main part of this function is from the PlugInFilterRunner.java of ImageJ(Vision: 1.51k).
     */

    public ImageData ImageSharpen (ImageData ori, double sigma, float weight) {

        int size = ori.getWidth() * ori.getHeight();
        int[] dstPixel = new int[size];
        int channels = 4;
        ImageData dst = new ImageData();
        dst.setSize(ori.getWidth(), ori.getHeight());

        for (int channelNumber = 0; channelNumber < channels; channelNumber++) {
            float[]  fpixel = toFloat(channelNumber, ori);

            fpixel = sharpenFloat(ori, fpixel, sigma, weight);

            dstPixel = setPixels(channelNumber, fpixel, dstPixel, size);
        }
        dst.setPixel(dstPixel);
        return dst;
    }

    /**
     * The main part of this function is from the UnsharpMask.java of ImageJ(Vision: 1.51k).
     */

    public float[] sharpenFloat (ImageData ori, float[] pixels, double sigma, float weight) {
        int size = ori.getWidth() * ori.getHeight();
        float[] snapshotPixels = new float[size];
        for (int i = 0; i < size; i++){
            snapshotPixels[i] = pixels[i];
        }

        blurFloat(ori, pixels, sigma, 0.01);
        for (int y = 0; y < ori.getHeight(); y++)
            for (int x = 0, p = ori.getWidth() * y + x; x < ori.getWidth(); x++, p++)
                pixels[p] = (snapshotPixels[p] - weight * pixels[p]) / (1f - weight);

        return pixels;
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    public void blurFloat (ImageData ori, float[] pixels, double sigma, double accuracy) {
        blur1Direction(ori, pixels, sigma, accuracy, true, (int)Math.ceil(5 * sigma));
        blur1Direction(ori, pixels, sigma, accuracy, false, 0);
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    public void blur1Direction (final ImageData ori, final float[] pixel, final double sigma, final double accuracy,
                               final boolean xDirection, final int extraLines) {

        final int UPSCALE_K_RADIUS = 2;                     //number of pixels to add for upscaling
        final double MIN_DOWNSCALED_SIGMA = 4.;             //minimum standard deviation in the downscaled image
        final float[] pixels = pixel;
        final int width = ori.getWidth();
        final int height = ori.getHeight();
        final int length = xDirection ? width : height;     //number of points per line (line can be a row or column)
        final int pointInc = xDirection ? 1 : width;        //increment of the pixels array index to the next point in a line
        final int lineInc = xDirection ? width : 1;         //increment of the pixels array index to the next line
        final int lineFromA =  0 - extraLines;  //the first line to process
        final int lineFrom;
        if (lineFromA < 0) lineFrom = 0;
        else lineFrom = lineFromA;
        final int lineToA = (xDirection ? height : width) + extraLines; //the last line+1 to process
        final int lineTo;
        if (lineToA > (xDirection ? height:width)) lineTo = (xDirection ? height:width);
        else lineTo = lineToA;
        final int writeFrom = xDirection?  0 : 0;    //first point of a line that needs to be written
        final int writeTo = xDirection ? width : height;

        final int numThreads = Math.min(8, lineTo-lineFrom);
        final Thread[] lineThreads = new Thread[numThreads];

        /* large radius (sigma): scale down, then convolve, then scale up */
        final boolean doDownscaling = sigma > 2*MIN_DOWNSCALED_SIGMA + 0.5;
        final int reduceBy = doDownscaling ?                //downscale by this factor
                Math.min((int)Math.floor(sigma/MIN_DOWNSCALED_SIGMA), length)
                : 1;
        /* Downscaling and upscaling blur the image a bit - we have to correct the standard
         * deviation for this:
         * Downscaling gives std devation sigma = 1/sqrt(3); upscale gives sigma = 1/2 (in downscaled pixels).
         * All sigma^2 values add to full sigma^2, which should be the desired value  */
        final double sigmaGauss = doDownscaling ?
                Math.sqrt(sigma*sigma/(reduceBy*reduceBy) - 1./3. - 1./4.)
                : sigma;
        final int maxLength = doDownscaling ?
                (length+reduceBy-1)/reduceBy + 2*(UPSCALE_K_RADIUS + 1) //downscaled line can't be longer
                : length;
        final float[][] gaussKernel = makeGaussianKernel(sigmaGauss, accuracy, maxLength);
        final int kRadius = gaussKernel[0].length*reduceBy;             //Gaussian kernel radius after upscaling
        final int readFrom = (writeFrom-kRadius < 0) ? 0 : writeFrom-kRadius; //not including broadening by downscale&upscale
        final int readTo = (writeTo+kRadius > length) ? length : writeTo+kRadius;
        final int newLength = doDownscaling ?                           //line length for convolution
                (readTo-readFrom+reduceBy-1)/reduceBy + 2*(UPSCALE_K_RADIUS + 1)
                : length;
        final int unscaled0 = readFrom - (UPSCALE_K_RADIUS + 1)*reduceBy; //input point corresponding to cache index 0
        //the following is relevant for upscaling only
        //IJ.log("reduce="+reduceBy+", newLength="+newLength+", unscaled0="+unscaled0+", sigmaG="+(float)sigmaGauss+", kRadius="+gaussKernel[0].length);
        final float[] downscaleKernel = doDownscaling ? makeDownscaleKernel(reduceBy) : null;
        final float[] upscaleKernel = doDownscaling ? makeUpscaleKernel(reduceBy) : null;

        for ( int t = 0; t < numThreads; ++t ) {
            final int ti = t;
            final float[] cache1 = new float[newLength];  //holds data before convolution (after downscaling, if any)
            final float[] cache2 = doDownscaling ? new float[newLength] : null;  //holds data after convolution

            int pixel0 = (lineFrom + ti) * lineInc;
            for (int line = lineFrom + ti; line < lineTo; line += numThreads, pixel0 += numThreads * lineInc) {

                if (doDownscaling) {
                    downscaleLine(pixels, cache1, downscaleKernel, reduceBy, pixel0, unscaled0, length, pointInc, newLength);
                    convolveLine(cache1, cache2, gaussKernel, 0, newLength, 1, newLength - 1, 0, 1);
                    upscaleLine(cache2, pixels, upscaleKernel, reduceBy, pixel0, unscaled0, writeFrom, writeTo, pointInc);
                } else {
                    int p = pixel0 + readFrom * pointInc;
                    for (int i = readFrom; i < readTo; i++, p += pointInc)
                        cache1[i] = pixels[p];
                    convolveLine(cache1, pixels, gaussKernel, readFrom, readTo, writeFrom, writeTo, pixel0, pointInc);
                }
            }
        }
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    final private void convolveLine( final float[] input, final float[] pixels, final float[][] kernel, final int readFrom,
                                            final int readTo, final int writeFrom, final int writeTo, final int point0, final int pointInc) {
        final int length = input.length;
        final float first = input[0];                 //out-of-edge pixels are replaced by nearest edge pixels
        final float last = input[length-1];
        final float[] kern = kernel[0];               //the kernel itself
        final float kern0 = kern[0];
        final float[] kernSum = kernel[1];            //the running sum over the kernel
        final int kRadius = kern.length;
        final int firstPart = kRadius < length ? kRadius : length;
        int p = point0 + writeFrom * pointInc;
        int i = writeFrom;
        for (; i<firstPart; i++, p += pointInc) {  //while the sum would include pixels < 0
            float result = input[i] * kern0;
            result += kernSum[i] * first;
            if (i+kRadius>length) result += kernSum[length-i-1]*last;
            for (int k = 1; k<kRadius; k++) {
                float v = 0;
                if (i-k >= 0) v += input[i-k];
                if (i+k<length) v+= input[i+k];
                result += kern[k] * v;
            }
            pixels[p] = result;
        }
        final int iEndInside = length-kRadius<writeTo ? length-kRadius : writeTo;
        for (; i < iEndInside; i++, p += pointInc) {   //while only pixels within the line are be addressed (the easy case)
            float result = input[i] * kern0;
            for (int k = 1; k < kRadius; k++)
                result += kern[k] * (input[i-k] + input[i+k]);
            pixels[p] = result;
        }
        for (; i<writeTo; i++, p += pointInc) {    //while the sum would include pixels >= length
            float result = input[i]*kern0;
            if (i < kRadius) result += kernSum[i] * first;
            if (i + kRadius >= length) result += kernSum[length-i-1]*last;
            for (int k = 1; k < kRadius; k++) {
                float v = 0;
                if (i - k >= 0) v += input[i-k];
                if (i + k < length) v += input[i+k];
                result += kern[k] * v;
            }
            pixels[p] = result;
        }
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    final private void downscaleLine(final float[] pixels, final float[] cache, final float[] kernel,
                                            final int reduceBy, final int pixel0, final int unscaled0, final int length, final int pointInc, final int newLength) {
        int p = pixel0 + pointInc * (unscaled0 - reduceBy * 3 / 2);  //pointer in pixels array
        final int pLast = pixel0 + pointInc* (length-1);
        for (int xout = -1; xout <= newLength; xout++) {
            float sum0 = 0, sum1 = 0, sum2 = 0;
            for (int x =0 ; x < reduceBy; x++, p+=pointInc) {
                float v = pixels[p < pixel0 ? pixel0 : (p > pLast ? pLast : p)];
                sum0 += v * kernel[x+2 * reduceBy];
                sum1 += v * kernel[x + reduceBy];
                sum2 += v * kernel[x];
            }
            if (xout > 0) cache[xout - 1] += sum0;
            if (xout >= 0 && xout < newLength) cache [xout] += sum1;
            if (xout + 1 <newLength) cache[xout + 1] = sum2;
        }
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    final private void upscaleLine (final float[] cache, final float[] pixels, final float[] kernel,
                                           final int reduceBy, final int pixel0, final int unscaled0, final int writeFrom, final int writeTo, final int pointInc) {
        int p = pixel0 + pointInc * writeFrom;
        for (int xout = writeFrom; xout < writeTo; xout++, p += pointInc) {
            final int xin = (xout - unscaled0 + reduceBy - 1)/reduceBy; //the corresponding point in the cache (if exact) or the one above
            final int x = reduceBy - 1 - (xout - unscaled0 + reduceBy-1) % reduceBy;
            pixels[p] = cache[xin - 2] * kernel[x]
                    + cache[xin - 1] * kernel[x + reduceBy]
                    + cache[xin] * kernel[x + 2 * reduceBy]
                    + cache[xin + 1] * kernel[x + 3 * reduceBy];
        }
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    public float[][] makeGaussianKernel(final double sigma, final double accuracy, int maxRadius) {
        int kRadius = (int)Math.ceil(sigma * Math.sqrt(-2 * Math.log(accuracy))) + 1;
        if (maxRadius < 50) maxRadius = 50;         // too small maxRadius would result in inaccurate sum.
        if (kRadius > maxRadius) kRadius = maxRadius;
        float[][] kernel = new float[2][kRadius];
        for (int i = 0; i < kRadius; i ++)               // Gaussian function
            kernel[0][i] = (float)(Math.exp(- 0.5 * i * i / sigma / sigma));
        if (kRadius < maxRadius && kRadius > 3) {   // edge correction
            double sqrtSlope = Double.MAX_VALUE;
            int r = kRadius;
            while (r > kRadius / 2) {
                r--;
                double a = Math.sqrt(kernel[0][r]) / (kRadius - r);
                if (a < sqrtSlope)
                    sqrtSlope = a;
                else
                    break;
            }
            for (int r1 = r + 2; r1 < kRadius; r1++)
                kernel[0][r1] = (float)((kRadius - r1) * (kRadius - r1) * sqrtSlope * sqrtSlope);
        }
        double sum;                                 // sum over all kernel elements for normalization
        if (kRadius < maxRadius) {
            sum = kernel[0][0];
            for (int i = 1; i < kRadius; i++)
                sum += 2 * kernel[0][i];
        } else
            sum = sigma * Math.sqrt(2 * Math.PI);

        double rsum = 0.5 + 0.5 * kernel[0][0] / sum;
        for (int i = 0; i < kRadius; i++) {
            double v = (kernel[0][i] / sum);
            kernel[0][i] = (float)v;
            rsum -= v;
            kernel[1][i] = (float)rsum;
            //IJ.log("k["+i+"]="+(float)v+" sum="+(float)rsum);
        }
        return kernel;
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    final private float[] makeDownscaleKernel (final int unitLength) {
        final int mid = unitLength * 3 / 2;
        final float[] kernel = new float[3 * unitLength];
        for (int i = 0; i <= unitLength / 2; i++) {
            final double x = i/(double)unitLength;
            final float v = (float)((0.75 - x * x) / unitLength);
            kernel[mid - i] = v;
            kernel[mid + i] = v;
        }
        for (int i = unitLength / 2 + 1; i < (unitLength * 3 + 1) / 2; i++) {
            final double x = i / (double)unitLength;
            final float v = (float)((0.125 + 0.5 * (x - 1)*(x - 2)) / unitLength);
            kernel[mid-i] = v;
            kernel[mid+i] = v;
        }
        return kernel;
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    final private float[] makeUpscaleKernel (final int unitLength) {
        final float[] kernel = new float[4 * unitLength];
        final int mid = 2 * unitLength;
        kernel[0] = 0;
        for (int i = 0; i < unitLength; i++) {
            final double x = i / (double)unitLength;
            final float v = (float)((2./3. -x * x * (1 - 0.5 * x)));
            kernel[mid+i] = v;
            kernel[mid-i] = v;
        }
        for (int i = unitLength; i < 2 * unitLength; i++) {
            final double x = i/(double)unitLength;
            final float v = ( float)((2. - x) * (2. - x) * (2. - x) / 6.);
            kernel[mid + i] = v;
            kernel[mid - i] = v;
        }
        return kernel;
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    public float[] toFloat(int channelNumber, ImageData img) {
        int size = img.getWidth() * img.getHeight();
        float[] fPixels = new float[size];
        int shift = 24 - 8 * channelNumber;
        int byteMask = 255 << shift;
        for (int i = 0; i < size; i++)
            fPixels[i] = (float)((img.getPixels()[i]&byteMask) >>> shift);
        return fPixels;
    }

    /**
     * The main part of this function is from the GaussianBlur.java of ImageJ(Vision: 1.51k).
     */

    public int[] setPixels(int channelNumber, float[] fpixel, int[] dstPixels, int size) {
        float value;
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

}
