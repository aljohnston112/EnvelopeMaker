package com.example.hellooboe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMain extends AppCompatActivity {

    LinearLayout linearLayoutAmpRow;
    LinearLayout linearLayoutFreqRow;
    boolean isFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        isFloat = NativeMethods.isFloat();
        linearLayoutAmpRow = (LinearLayout) findViewById(R.id.ampRow);
        linearLayoutFreqRow = (LinearLayout) findViewById(R.id.freqRow);
        ViewFunction viewFunction = new ViewFunction(this);
        linearLayoutAmpRow.addView(viewFunction);
        ViewFunction viewFunction2 = new ViewFunction(this);
        boolean ampIsOn = true;
        viewFunction2.setDefaultAmp(ampIsOn, isFloat, viewFunction.getNumSamples(isFloat));
        linearLayoutFreqRow.addView(viewFunction2);

        ViewFunction viewFunction3 = new ViewFunction(this);
        viewFunction3.setAddNew();
        linearLayoutAmpRow.addView(viewFunction3);
        ViewFunction viewFunction4 = new ViewFunction(this);
        viewFunction4.setAddNew();
        linearLayoutFreqRow.addView(viewFunction4);

    }

    public void addView(ViewFunction viewFunction, int channel, int columnIndex) {
        ((LinearLayout) linearLayoutAmpRow.getChildAt(channel))
                .addView(viewFunction, columnIndex);
    }

    public void addChannel(int channel) {
        linearLayoutAmpRow.addView(LayoutInflater.from(this)
                .inflate(R.layout.main_row, null), channel);
    }

    public void removeViewFunction(int channel, int columnIndex) {
        ((LinearLayout) linearLayoutAmpRow.getChildAt(channel)).removeViewAt(columnIndex);
    }

    public void removeChannel(int channel) {
        linearLayoutAmpRow.removeViewAt(channel);
    }

}