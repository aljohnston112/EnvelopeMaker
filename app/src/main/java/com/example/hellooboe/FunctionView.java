package com.example.hellooboe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class FunctionView extends View {

    Paint color = new Paint(Paint.ANTI_ALIAS_FLAG);

    private double minY = 0;

    private double maxY = 10;

    private float[] dataF = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private short[] dataS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private int height;

    private int width;

    public FunctionView(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
    }

    public FunctionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //super.onSizeChanged(w, h, oldw, oldh);
        double xtoYRatio = 1.0;
        if (NativeMethods.isFloat()) {
            xtoYRatio = ((double) dataF.length) / (maxY - minY);
        }
        width = w;
        height = (int) Math.round((double) w / xtoYRatio);
        if (height > h) {
            height = h;
        }
        if (w % 2 == 1) {
            w--;
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        double xtoYRatio = 1.0;
        if (NativeMethods.isFloat()) {
            xtoYRatio = ((double) dataF.length) / (maxY - minY);
        }
        int preferredWidth = (int) Math.round(MeasureSpec.getSize(heightMeasureSpec) * (double) xtoYRatio);
        int w = resolveSizeAndState(preferredWidth
                , widthMeasureSpec, 1);
        if (((preferredWidth <= MeasureSpec.getSize(w))
                && (MeasureSpec.getMode(w) | MeasureSpec.EXACTLY) != MeasureSpec.EXACTLY)
                || ((MeasureSpec.getMode(w) | MeasureSpec.UNSPECIFIED) == MeasureSpec.UNSPECIFIED)) {
            w = preferredWidth;
        }
        int preferredHeight = (int) Math.round((double) w / xtoYRatio);
        int h = resolveSizeAndState(preferredHeight
                , heightMeasureSpec, 1);
        if ((h | View.MEASURED_STATE_TOO_SMALL) != View.MEASURED_STATE_TOO_SMALL) {
            h = preferredHeight;
        }
        if (w % 2 == 1) {
            w--;
        }
        width = w;
        height = h;
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        height = canvas.getHeight();
        width = canvas.getWidth();
        color.setColor(Color.GREEN);
        if (NativeMethods.isFloat()) {
            double xScale = ((double) width) / ((double) dataF.length - 1);
            double yScale = ((double) height) / ((double) (maxY - minY));
            for (int i = 1; i < dataF.length; i++) {
                canvas.drawLine((float) (((double) i - 1) * xScale), (float) (height - ((((double) dataF[i - 1]) * yScale) - minY)),
                        (float) (((double) i) * xScale), (float) (height - ((((double) dataF[i]) * yScale) - minY)),
                        color);
            }
        }
    }

    void setData(double miny, double maxy, float[] values) {
        this.minY = minY;
        this.maxY = maxY;
        this.dataF = values;
    }

    void setData(double miny, double maxy, int[] values) {

    }

}
