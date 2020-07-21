package com.example.hellooboe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class ViewFunction extends View {

    public static final String COL_NUMBER_KEY = "590";
    public static final int ACTIVITY_AMP_MAKER = 591;
    public static final int ACTIVITY_FREQ_MAKER = 592;
    public static final String FLOAT_DATA = "593";
    public static final String SHORT_DATA = "594";
    public static final String COL_DATA = "595";
    public static final String MIN_DATA = "596";
    public static final String MAX_DATA = "597";

    private Paint lineColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int borderStrokeWidth = 4;
    private Paint textColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private boolean isConstant;
    private  boolean isAddNew;
    private boolean isAmp;
    private boolean selected;
    private int col;
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

    public ViewFunction(Context context, boolean isAmp, int col) {
        super(context);
        this.isAmp = isAmp;
        this.col = col;
        init();
    }

    public ViewFunction(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void init() {
        setOnClickListener(new OnClickListenerViewFunction());
        setOnLongClickListener(new OnLongClickListenerViewFunction());
        isConstant = false;
        setBackgroundColor(Color.WHITE);
        lineColor.setStrokeWidth(8);
        lineColor.setColor(getResources().getColor(R.color.colorPrimaryDark));
        borderColor.setStrokeWidth(borderStrokeWidth);
        borderColor.setColor(Color.BLACK);
        textColor.setTextSize(64);
        textColor.setColor(Color.BLACK);
        selected = false;
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

    public int getCol(){
        return col;
    }

    public boolean isSelected(){
        return selected;
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
        if(w%2 == 1) {
            w--;
        }
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        height = getHeight();
        width = getWidth();
        if (!isAddNew) {
            double xScale;
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
            canvas.drawRect((int) Math.round(3.5 * width / 8.0), (int) Math.round(2.0 * width / 8.0),
                    (int) Math.round(4.5 * width / 8.0), (int) Math.round(6.0 * width / 8.0), lineColor);
            canvas.drawRect((int) Math.round(2.0 * height / 8.0), (int) Math.round(3.5 * height / 8.0),
                    (int) Math.round(6.0 * width / 8.0), (int) Math.round(4.5 * width / 8.0), lineColor);
        }
       drawBorder(canvas);
    }

    private void drawBorder(Canvas canvas) {
        // Draw the border
        borderColor.setStrokeWidth(borderStrokeWidth);
        if(selected){
            borderStrokeWidth *=4;
            borderColor.setStrokeWidth(borderStrokeWidth);
        }
        //Right
        canvas.drawLine(width, 0, width, height, borderColor);
        //Left
        canvas.drawLine(0, height, 0, 0, borderColor);
        // Bottom
        if(isAmp){
            borderColor.setStrokeWidth((float) (borderStrokeWidth/2.0));
        }
        canvas.drawLine(width, height, 0, height, borderColor);
        //Top
        if(!isAmp){
            borderColor.setStrokeWidth((float) (borderStrokeWidth/2.0));
        } else {
            borderColor.setStrokeWidth(borderStrokeWidth);
        }
        canvas.drawLine(0, 0, width, 0, borderColor);
        if(selected){
            borderStrokeWidth /=4;
            borderColor.setStrokeWidth(borderStrokeWidth);
        }
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
            if (!((ActivityMain) getContext()).isLongClicked) {
                if (isAmp) {
                    Intent intent = new Intent(getContext(), ActivityAmpMaker.class);
                    intent.putExtra(COL_NUMBER_KEY, ((ViewFunction)view).getCol());
                    ((ActivityMain) getContext()).startActivityForResult(intent, ACTIVITY_AMP_MAKER);
                } else {
                    Intent intent = new Intent(getContext(), ActivityFreqMaker.class);
                    intent.putExtra(COL_NUMBER_KEY, ((ViewFunction)view).getCol());
                    ((ActivityMain) getContext()).startActivityForResult(intent, ACTIVITY_FREQ_MAKER);
                }
            } else{
                if(selected){
                    selected = false;
                } else {
                    selected = true;
                }
                invalidate();
            }
        }

    }

    private class OnLongClickListenerViewFunction implements OnLongClickListener {

        @Override
        public boolean onLongClick(View view) {
            ((ActivityMain) getContext()).longClicked(true);
            selected = true;
            invalidate();
            return true;
        }
    }

    public void select(boolean select){
        selected = select;
        invalidate();
    }

}
