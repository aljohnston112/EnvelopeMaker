package com.example.hellooboe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityFreqMaker extends AppCompatActivity {

    private int col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq_maker);
        TextInputLayout textInputLayoutFreqFunction = (TextInputLayout) findViewById(R.id.text_input_layout_freq_function);
        ArrayAdapter<CharSequence> arrayAdapterFreq = ArrayAdapter.createFromResource(this,
                R.array.functions, R.layout.auto_complete_text_view_function);
        AutoCompleteTextView autoCompleteTextViewFreqFunction = (AutoCompleteTextView) textInputLayoutFreqFunction.getEditText();
        assert autoCompleteTextViewFreqFunction != null;
        autoCompleteTextViewFreqFunction.setAdapter(arrayAdapterFreq);
        textInputLayoutFreqFunction.requestFocus();
    }

    public void onButtonFreqMakerCreate(View view) {
        AutoCompleteTextView autoCompleteTextViewFreqFunction = findViewById(R.id.auto_complete_text_view_freq_function);
        String function = autoCompleteTextViewFreqFunction.getText().toString();
        TextInputEditText textInputEditTextStartFreq = findViewById(R.id.edit_text_start_freq);
        TextInputEditText textInputEditTextEndFreq = findViewById(R.id.edit_text_end_freq);
        TextInputEditText textInputEditTextFreqLength = findViewById(R.id.edit_text_freq_length);
        TextInputEditText textInputEditTextMinFreq = findViewById(R.id.edit_text_min_freq);
        TextInputEditText textInputEditTextMaxFreq = findViewById(R.id.edit_text_max_freq);
        String startFreqString = textInputEditTextStartFreq.getText().toString();
        double startFreq = -1;
        if (!startFreqString.isEmpty()) {
            startFreq = Double.valueOf(startFreqString);
        }
        String endFreqString = textInputEditTextEndFreq.getText().toString();
        double endFreq = -1;
        if (!endFreqString.isEmpty()) {
            endFreq = Double.valueOf(endFreqString);
        }
        String freqLengthString = textInputEditTextFreqLength.getText().toString();
        double freqLength = -1;
        if (!freqLengthString.isEmpty()) {
            freqLength = Double.valueOf(freqLengthString);
        }
        String minFreqString = textInputEditTextMinFreq.getText().toString();
        double minFreq = -1;
        if (!minFreqString.isEmpty()) {
            minFreq = Double.valueOf(minFreqString);
        }
        String maxFreqString = textInputEditTextMaxFreq.getText().toString();
        double maxFreq = -1;
        if (!maxFreqString.isEmpty()) {
            maxFreq = Double.valueOf(maxFreqString);
        }

        boolean mustReturn = false;
        if (startFreq == -1) {
            mustReturn = true;
            TextInputLayout textInputLayoutStartFreq = findViewById(R.id.text_input_layout_start_freq);
            textInputLayoutStartFreq.setError("Frequency needed");
        }

        if (freqLength == -1) {
            mustReturn = true;
            TextInputLayout textInputLayoutStartFreq = findViewById(R.id.text_input_layout_freq_length);
            textInputLayoutStartFreq.setError("Positive length needed");
        }

        if (mustReturn) {
            return;
        }

        if (function.contentEquals(getResources().getString(R.string.Constant))) {
            float[] data = NativeMethods.loadConstant(startFreq, freqLength, 1, col);
            double min = NativeMethods.getMinFreq();
            double max = NativeMethods.getMaxFreq();
            Intent intent = new Intent();
            intent.putExtra(ViewFunction.FLOAT_DATA, data);
            intent.putExtra(ViewFunction.MIN_DATA, min);
            intent.putExtra(ViewFunction.MAX_DATA, max);
            intent.putExtra(ViewFunction.COL_DATA, col);
            setResult(Activity.RESULT_OK, intent);
            finish();
        } else if (function.contentEquals(getResources().getString(R.string.Exponential))) {

        } else if (function.contentEquals(getResources().getString(R.string.Linear))) {

        } else if (function.contentEquals(getResources().getString(R.string.Logarithm))) {

        } else if (function.contentEquals(getResources().getString(R.string.Nth_Root))) {

        } else if (function.contentEquals(getResources().getString(R.string.Power))) {

        } else if (function.contentEquals(getResources().getString(R.string.Quadratic))) {

        } else if (function.contentEquals(getResources().getString(R.string.Sine))) {

        }


    }
}

