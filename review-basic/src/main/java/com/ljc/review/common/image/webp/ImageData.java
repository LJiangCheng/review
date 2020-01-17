package com.ljc.review.common.image.webp;

/**
 * Created by Rocky on 5/24/2017.
 * This class contains the info of image
 */

public class ImageData {

    int[] Pixels;
    private int width;
    private int height;
    private byte[] R;
    private byte[] G;
    private byte[] B;
    private byte[] A;

    public ImageData() {
        width = 0;
        height = 0;
        A = null;
        R = null;
        G = null;
        B = null;
    }

    public void setPixel(int[] pixel) {
        Pixels = pixel;
    }

    public void setSize(int w, int h) {
        width = w;
        height = h;
    }

    public void setChannels(byte[] a, byte[] r, byte[] g, byte[] b) {
        A = a;
        R = r;
        G = g;
        B = b;
    }

    public void setChannels() {
        int c, a, r, g, b;
        byte[] Achannel = new byte[width * height];
        byte[] Rchannel = new byte[width * height];
        byte[] Gchannel = new byte[width * height];
        byte[] Bchannel = new byte[width * height];
        for (int i = 0; i < width * height; i++) {
            c = Pixels[i];
            a = (c & 0xff000000) >> 24;
            r = (c & 0xff0000) >> 16;
            g = (c & 0xff00) >> 8;
            b = c & 0xff;
            Achannel[i] = (byte) a;
            Rchannel[i] = (byte) r;
            Gchannel[i] = (byte) g;
            Bchannel[i] = (byte) b;
        }
        A = Achannel;
        R = Rchannel;
        G = Gchannel;
        B = Bchannel;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return Pixels;
    }

    public byte[] getAchannel() {
        return A;
    }

    public void setAchannel(byte[] a) {
        A = a;
    }

    public byte[] getRchannel() {
        return R;
    }

    public void setRchannel(byte[] r) {
        R = r;
    }

    public byte[] getGchannel() {
        return G;
    }

    public void setGchannel(byte[] g) {
        G = g;
    }

    public byte[] getBchannel() {
        return B;
    }

    public void setBchannel(byte[] b) {
        B = b;
    }

}