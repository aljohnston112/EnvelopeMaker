package io.fourth_finger.sound_sculptor

object NativeMethods {

    external fun createStream()


    external fun audioDataIsFloat(): Boolean

    external fun destroyStream()
    external fun startStream()
    external fun stopStream()
    val minAmp: Double
        external get
    val maxAmp: Double
        external get
    val minFreq: Double
        external get
    val maxFreq: Double
        external get

    external fun generateConstant(
        start: Double,
        length: Double,
        row: Int,
        col: Int,
        width: Int
    ): FloatArray?

    external fun makeSound()
    external fun getAmpTime(index: Int, samplesPerSecond: Int): Double
    external fun getFreqTime(index: Int, samplesPerSecond: Int): Double
    val sampleRate: Int
        external get

    external fun setData(
        row: Int,
        col: Int,
        start: Double,
        end: Double,
        length: Double,
        cycles: Double,
        min: Double,
        max: Double
    ) /*
    public static native float[] loadExponential(double start, double end, double length, int row, int col);

    public static native float[] loadLinear(double start, double end, double length, int row, int col);

    public static native float[] loadLogarithm(double start, double end, double length, int row, int col);

    public static native float[] loadNthRoot(double start, double end, double length, int row, int col);

    public static native float[] loadPower(double start, double end, double length, int row, int col);

    public static native float[] loadQuadratic(double start, double end, double length, int row, int col);

    public static native float[] loadSine(double start, double end, double length, int row, int col);
     */
}