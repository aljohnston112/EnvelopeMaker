package com.example.hellooboe;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class ViewFunction<T> extends View {

    private int height;

    private int width;

    Paint lineColor = new Paint(Paint.ANTI_ALIAS_FLAG);

    private double minY = 0;

    private double maxY = 10;

    private float[] dataF = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private short[] dataS = {0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 10};

    public ViewFunction(Context context) {
        super(context);
        init();
    }

    public ViewFunction(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        setBackgroundColor(Color.WHITE);
        lineColor.setColor(Color.BLUE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        //super.onSizeChanged(w, h, oldw, oldh);
        double xtoYRatio = 1.0;
        if (NativeMethods.isFloat()) {
            xtoYRatio = ((double) dataF.length) / (maxY - minY);
        } else {
            xtoYRatio = ((double) dataS.length) / (maxY - minY);

        }
        width = w;
        height = (int) Math.round((double) w / xtoYRatio);
        if (height > h || height == Double.POSITIVE_INFINITY) {
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
        } else {
            xtoYRatio = ((double) dataS.length) / (maxY - minY);
        }
        int preferredWidth = (int) Math.round(MeasureSpec.getSize(heightMeasureSpec) * (double) xtoYRatio);
        int w = resolveSizeAndState(preferredWidth
                , widthMeasureSpec, 1);
        if (((preferredWidth <= MeasureSpec.getSize(w))
                && (MeasureSpec.getMode(w) | MeasureSpec.EXACTLY) != MeasureSpec.EXACTLY)
                || ((MeasureSpec.getMode(w) | MeasureSpec.UNSPECIFIED) == MeasureSpec.UNSPECIFIED)) {
            w = preferredWidth;
        }
        if(w == 0){
            w = MeasureSpec.getSize(heightMeasureSpec);
        }
        int preferredHeight = (int) Math.round((double) w / xtoYRatio);
        int h = resolveSizeAndState(preferredHeight
                , heightMeasureSpec, 1);
        if (((h | View.MEASURED_STATE_TOO_SMALL) != View.MEASURED_STATE_TOO_SMALL) && preferredHeight != Double.POSITIVE_INFINITY) {
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
        double xScale = 1.0;
        double yScale = ((double) height) / ((double) (maxY - minY));
        if (NativeMethods.isFloat()) {
            xScale = ((double) width) / ((double) dataF.length - 1);
            for (int i = 1; i < dataF.length; i++) {
                canvas.drawLine((float) (((double) i - 1) * xScale), (float) (height - ((((double) dataF[i - 1]) * yScale) - minY)),
                        (float) (((double) i) * xScale), (float) (height - ((((double) dataF[i]) * yScale) - minY)),
                        lineColor);
            }
        } else {
            xScale = ((double) width) / ((double) dataS.length - 1);
            for (int i = 1; i < dataS.length; i++) {
                canvas.drawLine((float) (((double) i - 1) * xScale), (float) (height - ((((double) dataS[i - 1]) * yScale) - minY)),
                        (float) (((double) i) * xScale), (float) (height - ((((double) dataS[i]) * yScale) - minY)),
                        lineColor);
            }
        }
    }

    void setDataF(double miny, double maxy, float[] values) {
        this.minY = minY;
        this.maxY = maxY;
        this.dataF = values;
        invalidate();
    }

    void setDataS(double miny, double maxy, short[] values) {
        this.minY = minY;
        this.maxY = maxY;
        this.dataS = values;
        invalidate();
    }

}
