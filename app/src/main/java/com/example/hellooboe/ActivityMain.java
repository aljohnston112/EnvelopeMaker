package com.example.hellooboe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ActivityMain extends AppCompatActivity {

    LinearLayout linearLayoutAmpRow;
    LinearLayout linearLayoutFreqRow;
    boolean audioDataIsFloat;
    boolean isLongClicked;
    List<ViewFunction> viewFunctionsAmp = new ArrayList<>();
    List<ViewFunction> viewFunctionsFreq = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(findViewById(R.id.toolbar_main));
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        init();
        initFAB();
    }

    private void initFAB() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            NativeMethods.makeSound();
            NativeMethods.startStream();
        });
    }

    private void init() {
        audioDataIsFloat = NativeMethods.audioDataIsFloat();
        isLongClicked = false;
        linearLayoutAmpRow = findViewById(R.id.linear_layout_amp_row);
        linearLayoutFreqRow = findViewById(R.id.linear_layout_freq_row);
        ViewFunction viewFunctionAmp = new ViewFunction(this, true, 0);
        viewFunctionAmp.setAsAddNew();
        viewFunctionsAmp.add(viewFunctionAmp);
        linearLayoutAmpRow.addView(viewFunctionAmp);
        ViewFunction viewFunctionFreq = new ViewFunction(this, false, 0);
        viewFunctionFreq.setAsAddNew();
        viewFunctionsFreq.add(viewFunctionFreq);
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

    public void longClicked(boolean longClicked) {
        if (longClicked != isLongClicked) {
            isLongClicked = longClicked;
            Toolbar toolbar = findViewById(R.id.toolbar_main);
            MenuItem registrarCopy = toolbar.getMenu().findItem(R.id.action_copy);
            MenuItem registrarPaste = toolbar.getMenu().findItem(R.id.action_paste);
            MenuItem registrarDelete = toolbar.getMenu().findItem(R.id.action_delete);
            if (isLongClicked) {
                registrarCopy.setVisible(true);
                registrarPaste.setVisible(true);
                registrarDelete.setVisible(true);
            } else {
                registrarCopy.setVisible(false);
                registrarPaste.setVisible(false);
                registrarDelete.setVisible(false);
            }
        }
    }

    @Override
    public void onBackPressed() {
        boolean goBack = true;
        if (isLongClicked) {
            for (ViewFunction v : viewFunctionsAmp) {
                if (v.isSelected()) {
                    goBack = false;
                }
                v.select(false);
            }
            for (ViewFunction v : viewFunctionsFreq) {
                if (v.isSelected()) {
                    goBack = false;
                }
                v.select(false);
            }
            longClicked(false);
            if (goBack) {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                startActivity(new Intent(this, ActivitySaveFile.class));
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            double min = NativeMethods.getMinAmp() - 1;
            double max = NativeMethods.getMaxAmp() + 1;
            int col = data.getIntExtra(ViewFunction.COL_DATA, -1);
            if (requestCode == ViewFunction.ACTIVITY_AMP_MAKER) {
                if (col != -1 && min != Double.NaN && max != Double.NaN) {
                    if (NativeMethods.audioDataIsFloat()) {
                        float[] dataF = data.getFloatArrayExtra(ViewFunction.FLOAT_DATA);
                        ((ViewFunction) linearLayoutAmpRow.getChildAt(col)).setData(min, max, dataF);
                    } else {
                        short[] dataS = data.getShortArrayExtra(ViewFunction.SHORT_DATA);
                        ((ViewFunction) linearLayoutAmpRow.getChildAt(col)).setData(min, max, dataS);
                    }
                    //TODO copy to frequency maker
                    try {
                        ((ViewFunction) linearLayoutAmpRow.getChildAt(col))
                                .setFunction(data.getStringExtra(ViewFunction.FUNCTION_DATA));
                    } catch (NullPointerException ignored) {
                    }
                    ((ViewFunction) linearLayoutAmpRow.getChildAt(col))
                            .setStart(data.getDoubleExtra(ViewFunction.START_DATA, -1));
                    ((ViewFunction) linearLayoutAmpRow.getChildAt(col))
                            .setEnd(data.getDoubleExtra(ViewFunction.END_DATA, -1));
                    ((ViewFunction) linearLayoutAmpRow.getChildAt(col))
                            .setLength(data.getDoubleExtra(ViewFunction.LENGTH_DATA, -1));
                    ((ViewFunction) linearLayoutAmpRow.getChildAt(col))
                            .setMin(data.getDoubleExtra(ViewFunction.MIN_DATA, -1));
                    ((ViewFunction) linearLayoutAmpRow.getChildAt(col))
                            .setMax(data.getDoubleExtra(ViewFunction.MAX_DATA, -1));

                    ViewFunction viewFunctionAmp = new ViewFunction(this, true, col + 1);
                    viewFunctionAmp.setAsAddNew();
                    linearLayoutAmpRow.addView(viewFunctionAmp);
                }
            } else if (requestCode == ViewFunction.ACTIVITY_FREQ_MAKER) {
                if (col != -1 && min != Double.NaN && max != Double.NaN) {
                    if (NativeMethods.audioDataIsFloat()) {
                        float[] dataF = data.getFloatArrayExtra(ViewFunction.FLOAT_DATA);
                        ((ViewFunction) linearLayoutFreqRow.getChildAt(col)).setData(min, max, dataF);
                    } else {
                        short[] dataS = data.getShortArrayExtra(ViewFunction.SHORT_DATA);
                        ((ViewFunction) linearLayoutFreqRow.getChildAt(col)).setData(min, max, dataS);
                    }
                    ViewFunction viewFunctionFreq = new ViewFunction(this, false, col + 1);
                    viewFunctionFreq.setAsAddNew();
                    linearLayoutFreqRow.addView(viewFunctionFreq);
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // TODO Write your code if there's no result
            }
        }
    }

}