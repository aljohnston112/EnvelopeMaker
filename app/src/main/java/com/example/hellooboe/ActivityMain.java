package com.example.hellooboe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityMain extends AppCompatActivity {

    LinearLayout linearLayoutMainRows;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        linearLayoutMainRows = (LinearLayout) findViewById(R.id.linear_layout_main_rows);
    }

    public void addView(ViewFunction viewFunction, int channel, int columnIndex) {
        ((LinearLayout) linearLayoutMainRows.getChildAt(channel))
                .addView(viewFunction, columnIndex);
    }

    public void addChannel(int channel) {
        linearLayoutMainRows.addView(LayoutInflater.from(this)
                .inflate(R.layout.main_row, null), channel);
    }

    public void removeViewFunction(int channel, int columnIndex) {
        ((LinearLayout) linearLayoutMainRows.getChildAt(channel)).removeViewAt(columnIndex);
    }

    public void removeChannel(int channel) {
        linearLayoutMainRows.removeViewAt(channel);
    }

}