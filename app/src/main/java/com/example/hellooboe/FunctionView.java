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

    float height = 0;
    float width = 0;

    private int minY = 0;

    private int maxY = 1;

    private float[] dataF = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    private short[] dataS = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

    public FunctionView(Context context) {
        super(context);

    }

    public FunctionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // Account for padding
        float xpad = (float)(getPaddingLeft() + getPaddingRight());
        float ypad = (float)(getPaddingTop() + getPaddingBottom());

        width = (float)w - xpad;
        height = (float)h - ypad;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        color.setColor(Color.GREEN);
        if(NativeMethods.isFloat()){
            double xScale = ((double)width)/((double)dataF.length);
            double yScale = ((double)height)/((double)(maxY-minY));
            for(int i = 0; i < dataF.length; i+=2){
                canvas.drawLine((float)(((double)i)*xScale), (float)(((double)dataF[i])*yScale),
                        (float)(((double)(i+1))*xScale), (float)(((double)dataF[(i+1)])*yScale),
                        color);
            }
        }
    }

    void setData(double miny, double maxy, float[] values){
        this.minY = minY;
        this.maxY = maxY;
        this.dataF = values;
    }

    void setData(double miny, double maxy, int[] values){

    }

}
