package com.example.hellooboe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * This class draws a square and draws lines between points passed into the setData() methods.
 */
public class ViewFunction extends View {

    Paint lineColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint borderColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    boolean isConstant;
    boolean isAdd;
    private int width;
    private double maxY = 10;
    // View height and width
    private int height;
    // The min and max y values
    private double minY = 0;
    // The points to draw lines between
    private float[] dataF = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
    private short[] dataS = {0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 1};

    /**
     * Creates a ViewFunction.
     *
     * @param context
     */
    public ViewFunction(Context context) {
        super(context);
        init();
    }

    /**
     * Creates a ViewFunction.
     *
     * @param context
     * @param attrs
     */
    public ViewFunction(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * Initializes this ViewFunction
     */
    private void init() {
        isConstant = false;
        setBackgroundColor(Color.BLUE);
        lineColor.setColor(Color.WHITE);
        lineColor.setStrokeWidth(2);
        borderColor.setStrokeWidth(4);
        borderColor.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        double xtoYRatio = getXToYRatio();
        if (isAdd) {
            xtoYRatio = 1;
        }
        width = (int) Math.round((double) h * xtoYRatio);
        height = h;
        if (width > w) {
            width = w;
        }
        if (isConstant) {
            width = w;
            height = h;
        }
        invalidate();
    }

    /**
     * Get the XtoYRatio to keep drawing to scale.
     */
    public double getXToYRatio() {
        double xtoYRatio;
        if (NativeMethods.isFloat()) {
            xtoYRatio = ((double) dataF.length) / (maxY - minY);
        } else {
            xtoYRatio = ((double) dataS.length) / (maxY - minY);

        }
        return xtoYRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = width;
        int h = height;
        // Get width based on height
        double xtoYRatio = getXToYRatio();
        if (isAdd) {
            xtoYRatio = 1;
        }
        w = (int) Math.round(MeasureSpec.getSize(heightMeasureSpec) * xtoYRatio);
        if (w > MeasureSpec.getSize(widthMeasureSpec)) {
            w = (resolveSizeAndState(w
                    , widthMeasureSpec, getMeasuredState()) | MEASURED_SIZE_MASK);
        }
        if (w > (int) Math.round(MeasureSpec.getSize(heightMeasureSpec) * xtoYRatio)) {
            w = (int) Math.round(MeasureSpec.getSize(heightMeasureSpec) * xtoYRatio);
        }
        h = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        height = canvas.getHeight();
        width = canvas.getWidth();
        // Draw the border
        canvas.drawLine(0, 0, width, 0, borderColor);
        canvas.drawLine(width, 0, width, height, borderColor);
        canvas.drawLine(width, height, 0, height, borderColor);
        canvas.drawLine(0, height, 0, 0, borderColor);
        if (!isAdd) {
            double xScale = 1.0;
            if (NativeMethods.isFloat()) {
                xScale = ((double) width) / ((double) dataF.length - 1);
            } else {
                xScale = ((double) width) / ((double) dataS.length - 1);
            }
            double yScale = ((double) height) / ((double) (maxY - minY));
            for (int i = 1; i < dataF.length; i++) {
                canvas.drawLine((float) (((double) i - 1) * xScale), (float) (height - ((((double) dataF[i - 1]) * yScale) - minY)),
                        (float) (((double) i) * xScale), (float) (height - ((((double) dataF[i]) * yScale) - minY)),
                        lineColor);
            }
        } else {
            canvas.drawRect((int) Math.round(width / 3.0), 0, (int) Math.round(2.0 * width / 3.0), height, lineColor);
            canvas.drawRect(0, (int) Math.round(height / 3.0), height, (int) Math.round(2.0 * width / 3.0), lineColor);
        }
    }

    void setData(double miny, double maxy, float[] values) {
        this.minY = minY;
        this.maxY = maxY;
        this.dataF = values;
        invalidate();
    }

    void setData(double miny, double maxy, short[] values) {
        this.minY = minY;
        this.maxY = maxY;
        this.dataS = values;
        invalidate();
    }

    void setDefaultAmp(boolean on, boolean isFloat, int samples) {
        if (on) {
            if (isFloat) {
                dataF = new float[samples];
                for (int i = 0; i < samples; i++) {
                    dataF[i] = 1;
                }
            } else {
                dataS = new short[samples];
                for (int i = 0; i < samples; i++) {
                    dataS[i] = 1;
                }
            }
        } else {
            if (isFloat) {
                dataF = new float[samples];
                for (int i = 0; i < samples; i++) {
                    dataF[i] = 0;
                }
            } else {
                dataS = new short[samples];
                for (int i = 0; i < samples; i++) {
                    dataS[i] = 0;
                }
            }
        }
        width = samples;
        height = samples;
        isConstant = true;
        invalidate();
    }

    int getNumSamples(boolean isFloat) {
        if (isFloat) {
            return dataF.length;
        } else {
            return dataS.length;
        }
    }


    void setAddNew() {

        isAdd = true;
    }

}
