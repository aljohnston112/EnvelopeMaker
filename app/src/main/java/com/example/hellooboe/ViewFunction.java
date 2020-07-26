package com.example.hellooboe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ViewFunction extends View {

    public static final int ACTIVITY_AMP_MAKER = 591;
    public static final int ACTIVITY_FREQ_MAKER = 592;
    public static final int ACTIVITY_AMP_MAKER_FILL = 598;
    public static final int ACTIVITY_FREQ_MAKER_FILL = 599;
    public static final String COL_NUMBER_KEY = "590";
    public static final String FLOAT_DATA = "593";
    public static final String SHORT_DATA = "594";
    public static final String COL_DATA = "595";
    public static final String MIN_DATA = "596";
    public static final String MAX_DATA = "597";

    private Paint lineColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint borderColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint textColor = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int borderStrokeWidth = 4;

    private int col;
    private double minY = 0;
    private double maxY = 10;
    private int height;
    private int width;

    private boolean isConstant;
    private boolean isAddNew;
    private boolean isAmp;
    private boolean selected;

    // The points to draw lines between
    private float[] dataF = null;
    private short[] dataS = null;

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
        double time = 1.0 / 4.0;
        if (isAmp) {
            if (dataF != null || dataS != null)
                time = NativeMethods.getAmpTime(col, NativeMethods.getSampleRate());
        } else {
            if (dataF != null || dataS != null)
                time = NativeMethods.getFreqTime(col, NativeMethods.getSampleRate());
        }
        return time * 4.0;
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
            drawBetweenPoints(canvas);
            drawYValues(canvas);
        } else {
            drawPlus(canvas);
        }
        drawBorder(canvas);
    }

    private void drawBetweenPoints(Canvas canvas) {
        double xScale;
        if (NativeMethods.audioDataIsFloat()) {
            xScale = ((double) width) / ((double) dataF.length - 1);
        } else {
            xScale = ((double) width) / ((double) dataS.length - 1);
        }
        double yScale = ((double) height) / (maxY - minY);
        // Draw between the points
        for (int i = 1; i < dataF.length; i++) {
            canvas.drawLine((float) (((double) i - 1) * xScale), (float) (height - ((((double) dataF[i - 1]) * yScale) - minY)),
                    (float) (((double) i) * xScale), (float) (height - ((((double) dataF[i]) * yScale) - minY)),
                    lineColor);
        }
    }

    private void drawPlus(Canvas canvas) {
        canvas.drawRect((int) Math.round(3.5 * width / 8.0), (int) Math.round(2.0 * width / 8.0),
                (int) Math.round(4.5 * width / 8.0), (int) Math.round(6.0 * width / 8.0), lineColor);
        canvas.drawRect((int) Math.round(2.0 * height / 8.0), (int) Math.round(3.5 * height / 8.0),
                (int) Math.round(6.0 * width / 8.0), (int) Math.round(4.5 * width / 8.0), lineColor);
    }

    private void drawYValues(Canvas canvas) {
        // Draw the y values for the end points
        String startText;
        String endText;
        if (NativeMethods.audioDataIsFloat()) {
            startText = String.format("(%.2f)", dataF[0]);
            endText = String.format("(%.2f)", dataF[dataF.length - 1]);

        } else {
            startText = String.format("(%.2f)", dataF[0]);
            endText = String.format("(%.2f)", dataS[dataS.length - 1]);
        }
        float textWidthStart = textColor.measureText(startText);
        float textWidthEnd = textColor.measureText(endText);
        while (((textWidthStart + textWidthEnd + borderStrokeWidth) * 2.0) > width) {
            textColor.setTextSize(textColor.getTextSize() - 1);
            textWidthStart = textColor.measureText(startText);
            textWidthEnd = textColor.measureText(endText);
        }
        canvas.drawText(startText, borderStrokeWidth / 2, (int) Math.round(height / 2.0), textColor);
        canvas.drawText(endText, width - textWidthEnd - borderStrokeWidth / 2, (int) Math.round(height / 2.0), textColor);
    }

    private void drawBorder(Canvas canvas) {
        // Draw the border
        borderColor.setStrokeWidth(borderStrokeWidth);
        if (selected) {
            borderStrokeWidth *= 4;
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
        int numPoints = (int) Math.round(width / 2.0);
        int skip = values.length / numPoints;
        this.minY = minY;
        this.maxY = maxY;
        this.dataF = new float[numPoints];
        for (int i = 0; i < dataF.length; i++) {
            dataF[i] = values[i * skip];
        }
        isAddNew = false;
        isConstant = false;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = (int) Math.round(height * getXToYRatio());
        requestLayout();
        invalidate();
    }

    void setData(double minY, double maxY, short[] values) {
        int numPoints = (int) Math.round(width / 2.0);
        int skip = values.length / numPoints;
        this.minY = minY;
        this.maxY = maxY;
        this.dataF = new float[numPoints];
        for (int i = 0; i < dataF.length; i++) {
            dataF[i] = values[i * skip];
        }
        isAddNew = false;
        isConstant = false;
        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = (int) Math.round(height * getXToYRatio());
        requestLayout();
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

    boolean isAddNew() {
        return isAddNew;
    }

    void setAsAddNew() {
        isAddNew = true;
        isConstant = false;
        invalidate();
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
                selected = !selected;
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
