package com.example.hellooboe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class ViewFunction extends View {

    Paint lineColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint borderColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    Paint textColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    boolean isConstant;
    boolean isAddNew;
    boolean isAmp;
    private double minY = 0;
    private double maxY = 10;
    private int height;
    private int width;
    // The points to draw lines between
    private float[] dataF = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 1};
    private short[] dataS = {0, 1, 2, 3, 4, 5, 4, 3, 2, 1, 1};

    public ViewFunction(Context context) {
        super(context);
        init();
    }

    public ViewFunction(Context context, boolean isAmp) {
        super(context);
        this.isAmp = isAmp;
        init();
    }

    private void init() {
        setOnClickListener(new OnClickListenerViewFunction());
        setOnLongClickListener(new OnLongClickListenerViewFunction());
        isConstant = false;
        setBackgroundColor(Color.WHITE);
        lineColor.setStrokeWidth(8);
        lineColor.setColor(Color.BLUE);
        borderColor.setStrokeWidth(4);
        borderColor.setColor(Color.BLACK);
        textColor.setTextSize(64);
        textColor.setColor(Color.BLACK);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        double xtoYRatio = getXToYRatio();
        if (isAddNew) {
            xtoYRatio = 1;
        }
        width = (int) Math.round((double) h * xtoYRatio);
        height = h;
        if (width > w) {
            width = w;
            height = (int) Math.round((double) w / xtoYRatio);
            if (height > h) {
                height = h;
            }
        }
        if (isConstant) {
            width = w;
            height = h;
        }
        invalidate();
    }

    /**
     * Gets the XtoYRatio of the data to keep drawing to scale.
     */
    public double getXToYRatio() {
        double xtoYRatio;
        if (NativeMethods.audioDataIsFloat()) {
            xtoYRatio = ((double) dataF.length) / (maxY - minY);
        } else {
            xtoYRatio = ((double) dataS.length) / (maxY - minY);
        }
        return xtoYRatio;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w;
        int h;
        double xtoYRatio = getXToYRatio();
        if (isAddNew) {
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
        height = getHeight();
        width = getWidth();
        if (!isAddNew) {
            double xScale = 1.0;
            if (NativeMethods.audioDataIsFloat()) {
                xScale = ((double) width) / ((double) dataF.length - 1);
            } else {
                xScale = ((double) width) / ((double) dataS.length - 1);
            }
            double yScale = ((double) height) / ((double) (maxY - minY));
            // Draw between the points
            for (int i = 1; i < dataF.length; i++) {
                canvas.drawLine((float) (((double) i - 1) * xScale), (float) (height - ((((double) dataF[i - 1]) * yScale) - minY)),
                        (float) (((double) i) * xScale), (float) (height - ((((double) dataF[i]) * yScale) - minY)),
                        lineColor);
            }
            // Draw the y values for the end points
            if (NativeMethods.audioDataIsFloat()) {
                String endText = String.format("(%.2f)", dataF[dataF.length - 1]);
                float textWidth = textColor.measureText(endText);
                canvas.drawText(String.format("(%.2f)", dataF[0]), 2, (int) Math.round(height / 2.0), textColor);
                canvas.drawText(endText, width - textWidth - 2, (int) Math.round(height / 2.0), textColor);
            } else {
                String endText = String.format("(%.2f)", dataS[dataS.length - 1]);
                float textWidth = textColor.measureText(endText);
                canvas.drawText(String.format("(%.2f)", dataS[0]), 2, (int) Math.round(height / 2.0), textColor);
                canvas.drawText(endText, width - textWidth - 2, (int) Math.round(height / 2.0), textColor);
            }
        } else {
            canvas.drawRect((int) Math.round(3.0 * width / 8.0), 0, (int) Math.round(5.0 * width / 8.0), height, lineColor);
            canvas.drawRect(0, (int) Math.round(3.0 * height / 8.0), height, (int) Math.round(5.0 * width / 8.0), lineColor);
        }
        // Draw the border
        canvas.drawLine(width, 0, width, height, borderColor);
        canvas.drawLine(width, height, 0, height, borderColor);
        canvas.drawLine(0, 0, width, 0, borderColor);
        canvas.drawLine(0, height, 0, 0, borderColor);
    }

    void setData(double minY, double maxY, float[] values) {
        this.minY = minY;
        this.maxY = maxY;
        this.dataF = values;
        invalidate();
    }

    void setData(double minY, double maxY, short[] values) {
        this.minY = minY;
        this.maxY = maxY;
        this.dataS = values;
        invalidate();
    }

    void setData(double minY, double maxY) {
        this.minY = Math.min(minY, this.minY);
        this.maxY = Math.max(maxY, this.maxY);
        invalidate();
    }

    void setAsDefaultAmp(boolean ampOn, boolean audioDataIsFloat, int samples) {
        if (ampOn) {
            if (audioDataIsFloat) {
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
            if (audioDataIsFloat) {
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


    void setAsAddNew() {
        isAddNew = true;
    }

    private class OnClickListenerViewFunction implements OnClickListener {

        @Override
        public void onClick(View view) {
            if (isAmp) {
                getContext().startActivity(new Intent(getContext(), ActivityAmpMaker.class));
            } else {
                getContext().startActivity(new Intent(getContext(), ActivityFreqMaker.class));
            }
        }
    }

    private class OnLongClickListenerViewFunction implements OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            ((ActivityMain) getContext()).longClicked();
            return false;
        }
    }

}
