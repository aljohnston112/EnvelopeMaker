package com.example.hellooboe;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ActivityMain extends AppCompatActivity {

    LinearLayout linearLayoutAmpRow;
    LinearLayout linearLayoutFreqRow;
    boolean audioDataIsFloat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        ((Toolbar) findViewById(R.id.toolbar_main)).inflateMenu(R.menu.menu_main);
        audioDataIsFloat = NativeMethods.audioDataIsFloat();
        linearLayoutAmpRow = (LinearLayout) findViewById(R.id.linear_layout_amp_row);
        linearLayoutFreqRow = (LinearLayout) findViewById(R.id.linear_layout_freq_row);
        ViewFunction viewFunctionAmp = new ViewFunction(this, true);
        viewFunctionAmp.setAsAddNew();
        linearLayoutAmpRow.addView(viewFunctionAmp);
        ViewFunction viewFunctionFreq = new ViewFunction(this, false);
        viewFunctionFreq.setAsAddNew();
        linearLayoutFreqRow.addView(viewFunctionFreq);
    }

    public void addView(ViewFunction viewFunction, int channel, int columnIndex) {
        ((LinearLayout) linearLayoutAmpRow.getChildAt(channel))
                .addView(viewFunction, columnIndex);
    }

    public void addChannel(int channel) {
        linearLayoutAmpRow.addView(LayoutInflater.from(this)
                .inflate(R.layout.linear_layout_row, null), channel);
    }

    public void removeViewFunction(int channel, int columnIndex) {
        ((LinearLayout) linearLayoutAmpRow.getChildAt(channel)).removeViewAt(columnIndex);
    }

    public void removeChannel(int channel) {
        linearLayoutAmpRow.removeViewAt(channel);
    }

    public void longClicked() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        MenuItem registrar = toolbar.getMenu().findItem(R.id.action_copy);
        registrar.setVisible(true);
    }

}