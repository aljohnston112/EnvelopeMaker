package com.example.hellooboe;

public class NativeMethods {

    public static native void createStream();

    public static native void destroyStream();

    public static native void startStream();

    public static native void stopStream();

    public static native boolean audioDataIsFloat();

    public static native double getMinAmp();

    public static native double getMaxAmp();

    public static native double getMinFreq();

    public static native double getMaxFreq();

    public static native float[] loadConstant(double start, double length, int row, int col);

    public static native void makeSound();

    /*
    public static native float[] loadExponential(double start, double end, double length, int row, int col);

    public static native float[] loadLinear(double start, double end, double length, int row, int col);

    public static native float[] loadLogarithm(double start, double end, double length, int row, int col);

    public static native float[] loadNthRoot(double start, double end, double length, int row, int col);

    public static native float[] loadPower(double start, double end, double length, int row, int col);

    public static native float[] loadQuadratic(double start, double end, double length, int row, int col);

    public static native float[] loadSine(double start, double end, double length, int row, int col);
     */


}
