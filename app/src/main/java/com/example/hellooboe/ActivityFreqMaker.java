package com.example.hellooboe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class ActivityFreqMaker extends AppCompatActivity {

    private int col;

    ConstraintLayout constraintLayoutFreq;

    TextInputLayout textInputLayoutFreqFunction;
    TextInputLayout textInputLayoutStartFreq;
    TextInputLayout textInputLayoutEndFreq;
    TextInputLayout textInputLayoutFreqLength;
    TextInputLayout textInputLayoutMinFreq;
    TextInputLayout textInputLayoutMaxFreq;
    TextInputLayout textInputLayoutFreqCycles;

    AutoCompleteTextView autoCompleteTextViewFreqFunction;
    TextInputEditText textInputEditStartFreq;
    TextInputEditText textInputEditEndFreq;
    TextInputEditText textInputEditFreqLength;
    TextInputEditText textInputEditMinFreq;
    TextInputEditText textInputEditMaxFreq;
    TextInputEditText textInputEditFreqCycles;

    Button buttonCreate;
    Button buttonCancel;

    String function;
    double start = -1;
    double end = -1;
    double length = -1;
    double cycles = -1;
    double min = -1;
    double max = -1;

    int width = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq_maker);
        findViews();
        setUpFunctionAutoComplete();
        resizeButtons();
        textInputLayoutStartFreq.setVisibility(View.INVISIBLE);
        textInputLayoutEndFreq.setVisibility(View.INVISIBLE);
        textInputLayoutFreqLength.setVisibility(View.INVISIBLE);
        textInputLayoutMinFreq.setVisibility(View.INVISIBLE);
        textInputLayoutMaxFreq.setVisibility(View.INVISIBLE);
        textInputLayoutFreqCycles.setVisibility(View.INVISIBLE);
        populate();
        col = getIntent().getIntExtra(ViewFunction.COL_NUMBER_KEY, -1);
        width = getIntent().getIntExtra(ViewFunction.WIDTH_DATA, -1);
    }

    private void populate() {
        function = getIntent().getStringExtra(ViewFunction.FUNCTION_DATA);
        if (function != null) {
            autoCompleteTextViewFreqFunction.setText(function);
            autoCompleteTextViewFreqFunction.performCompletion();
            if (function.contentEquals(getResources().getString(R.string.Constant))) {
                setUpConstant();
            } else if (function.contentEquals(getResources().getString(R.string.Exponential))) {

            } else if (function.contentEquals(getResources().getString(R.string.Linear))) {

            } else if (function.contentEquals(getResources().getString(R.string.Logarithm))) {

            } else if (function.contentEquals(getResources().getString(R.string.Nth_Root))) {

            } else if (function.contentEquals(getResources().getString(R.string.Power))) {

            } else if (function.contentEquals(getResources().getString(R.string.Quadratic))) {

            } else if (function.contentEquals(getResources().getString(R.string.Sine))) {

            }
        }
        start = getIntent().getDoubleExtra(ViewFunction.START_DATA, -1);
        if (start != -1) {
            textInputEditStartFreq.setText(String.valueOf(start));
        }
        end = getIntent().getDoubleExtra(ViewFunction.END_DATA, -1);
        if (end != -1) {
            textInputEditEndFreq.setText(String.valueOf(end));
        }
        length = getIntent().getDoubleExtra(ViewFunction.LENGTH_DATA, -1);
        if (length != -1) {
            textInputEditFreqLength.setText(String.valueOf(length));
        }
        cycles = getIntent().getDoubleExtra(ViewFunction.CYCLES_DATA, -1);
        if (cycles != -1) {
            textInputEditFreqCycles.setText(String.valueOf(cycles));
        }
        min = getIntent().getDoubleExtra(ViewFunction.MIN_DATA, -1);
        if (min != -1) {
            textInputEditMinFreq.setText(String.valueOf(min));
        }
        max = getIntent().getDoubleExtra(ViewFunction.MAX_DATA, -1);
        if (max != -1) {
            textInputEditMaxFreq.setText(String.valueOf(max));
        }
    }

    private void findViews() {
        constraintLayoutFreq = findViewById(R.id.constraint_layout_freq);

        textInputLayoutFreqFunction = findViewById(R.id.text_input_layout_freq_function);
        textInputLayoutStartFreq = findViewById(R.id.text_input_layout_start_freq);
        textInputLayoutEndFreq = findViewById(R.id.text_input_layout_end_freq);
        textInputLayoutFreqLength = findViewById(R.id.text_input_layout_freq_length);
        textInputLayoutMinFreq = findViewById(R.id.text_input_layout_min_freq);
        textInputLayoutMaxFreq = findViewById(R.id.text_input_layout_max_freq);
        textInputLayoutFreqCycles = findViewById(R.id.text_input_layout_freq_cycles);

        autoCompleteTextViewFreqFunction = findViewById(R.id.auto_complete_text_view_freq_function);
        textInputEditStartFreq = findViewById(R.id.text_input_edit_start_freq);
        textInputEditEndFreq = findViewById(R.id.edit_text_end_freq);
        textInputEditFreqLength = findViewById(R.id.edit_text_freq_length);
        textInputEditMinFreq = findViewById(R.id.edit_text_min_freq);
        textInputEditMaxFreq = findViewById(R.id.edit_text_max_freq);
        textInputEditFreqCycles = findViewById(R.id.edit_text_freq_cycles);

        buttonCreate = findViewById(R.id.button_create_freq);
        buttonCancel = findViewById(R.id.button_cancel_freq);
    }

    private void resizeButtons() {
        final ViewTreeObserver obs = constraintLayoutFreq.getViewTreeObserver();
        obs.addOnPreDrawListener(() -> {
            ViewGroup.LayoutParams params = buttonCreate.getLayoutParams();
            params.height = textInputLayoutStartFreq.getHeight();
            buttonCreate.requestLayout();
            ViewGroup.LayoutParams params2 = buttonCancel.getLayoutParams();
            params2.height = textInputLayoutStartFreq.getHeight();
            buttonCancel.requestLayout();
            //We only care the first time it happens, so remove it
            //Post your animation here, then return true
            return true;
        });
    }

    private void setUpFunctionAutoComplete() {
        ArrayAdapter<CharSequence> arrayAdapterFreq = ArrayAdapter.createFromResource(this,
                R.array.functions, R.layout.auto_complete_text_view_function);
        AutoCompleteTextView autoCompleteTextViewFreqFunction = (AutoCompleteTextView) textInputLayoutFreqFunction.getEditText();
        assert autoCompleteTextViewFreqFunction != null;
        autoCompleteTextViewFreqFunction.setAdapter(arrayAdapterFreq);
        autoCompleteTextViewFreqFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrayAdapterFreq.getItem(i).toString().contentEquals(getResources().getString(R.string.Constant))){
                    setUpConstant();
                }
            }
        });
        textInputLayoutFreqFunction.requestFocus();
    }

    private void setUpConstant(){
        textInputLayoutStartFreq.setVisibility(View.VISIBLE);
        textInputLayoutEndFreq.setVisibility(View.INVISIBLE);
        textInputLayoutFreqLength.setVisibility(View.VISIBLE);
        textInputLayoutMinFreq.setVisibility(View.INVISIBLE);
        textInputLayoutMaxFreq.setVisibility(View.INVISIBLE);
        textInputLayoutFreqCycles.setVisibility(View.INVISIBLE);
    }

    public void onButtonFreqMakerCreate(View view) {
        function = autoCompleteTextViewFreqFunction.getText().toString();
        String startFreqString = Objects.requireNonNull(textInputEditStartFreq.getText()).toString();
        if (!startFreqString.isEmpty()) {
            start = Double.parseDouble(startFreqString);
        }
        String endFreqString = Objects.requireNonNull(textInputEditEndFreq.getText()).toString();
        if (!endFreqString.isEmpty()) {
            end = Double.parseDouble(endFreqString);
        }
        String freqLengthString = Objects.requireNonNull(textInputEditFreqLength.getText()).toString();
        if (!freqLengthString.isEmpty()) {
            length = Double.parseDouble(freqLengthString);
        }
        String freqCyclesString = Objects.requireNonNull(textInputEditFreqCycles.getText()).toString();
        if (!freqCyclesString.isEmpty()) {
            cycles = Double.parseDouble(freqCyclesString);
        }
        String minFreqString = Objects.requireNonNull(textInputEditMinFreq.getText()).toString();
        if (!minFreqString.isEmpty()) {
            min = Double.parseDouble(minFreqString);
        }
        String maxFreqString = Objects.requireNonNull(textInputEditMaxFreq.getText()).toString();
        if (!maxFreqString.isEmpty()) {
            max = Double.parseDouble(maxFreqString);
        }
        boolean mustReturn = false;
        float[] data = null;

        if (function.contentEquals(getResources().getString(R.string.Constant))) {
            if (start == -1) {
                mustReturn = true;
                textInputLayoutStartFreq.setError("Frequency needed");
            }
            if (length <= 0) {
                mustReturn = true;
                textInputLayoutStartFreq.setError("Positive length needed");
            }
            if (mustReturn) {
                return;
            }
            data = NativeMethods.loadConstant(start, length, 1, col, width);
        } else if (function.contentEquals(getResources().getString(R.string.Exponential))) {

        } else if (function.contentEquals(getResources().getString(R.string.Linear))) {

        } else if (function.contentEquals(getResources().getString(R.string.Logarithm))) {

        } else if (function.contentEquals(getResources().getString(R.string.Nth_Root))) {

        } else if (function.contentEquals(getResources().getString(R.string.Power))) {

        } else if (function.contentEquals(getResources().getString(R.string.Quadratic))) {

        } else if (function.contentEquals(getResources().getString(R.string.Sine))) {

        }
        double minY = NativeMethods.getMinFreq();
        double maxY = NativeMethods.getMaxFreq();
        Intent intent = new Intent();
        intent.putExtra(ViewFunction.FLOAT_DATA, data);
        intent.putExtra(ViewFunction.MIN_Y_DATA, minY);
        intent.putExtra(ViewFunction.MAX_Y_DATA, maxY);
        intent.putExtra(ViewFunction.COL_DATA, col);
        intent.putExtra(ViewFunction.FUNCTION_DATA, function);
        intent.putExtra(ViewFunction.START_DATA, start);
        intent.putExtra(ViewFunction.LENGTH_DATA, length);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void onButtonFreqMakerCancel(View view) {
        finish();
    }

}
