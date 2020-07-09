package com.example.hellooboe;

public class NativeMethods {
    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public static native void createStream();

    public static native void destroyStream();

    public static native boolean isFloat();

    public static native float[] loadDataFloat();

    public static native short[] loadDataShort();

    public static native double getMinAmp();

    public static native double getMaxAmp();

    public static native double getMinFreq();

    public static native double getMaxFreq();
}
