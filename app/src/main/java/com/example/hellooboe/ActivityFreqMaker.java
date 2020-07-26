package com.example.hellooboe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ActivityFreqMaker extends AppCompatActivity {

    private int col;

    String function;
    double startFreq = -1;
    double endFreq = -1;
    double minFreq = -1;
    double maxFreq = -1;
    double freqLength = -1;

    ConstraintLayout constraintLayoutFreq;

    TextInputLayout textInputLayoutStartFreq;
    TextInputLayout textInputLayoutEndFreq;
    TextInputLayout textInputLayoutFreqFunction;
    TextInputLayout textInputLayoutFreqLength;
    TextInputLayout textInputLayoutMinFreq;
    TextInputLayout textInputLayoutMaxFreq;

    AutoCompleteTextView autoCompleteTextViewFreqFunction;
    TextInputEditText textInputEditTextStartFreq;
    TextInputEditText textInputEditTextEndFreq;
    TextInputEditText textInputEditTextFreqLength;
    TextInputEditText textInputEditTextMinFreq;
    TextInputEditText textInputEditTextMaxFreq;

    Button buttonCreate;
    Button buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq_maker);
        findViews();
        setUpFunctionAutoComplete();
        resizeButtons();
        col = getIntent().getIntExtra(ViewFunction.COL_NUMBER_KEY, -1);
    }

    private void findViews() {
        constraintLayoutFreq = findViewById(R.id.constraint_layout_freq);

        textInputLayoutFreqFunction = findViewById(R.id.text_input_layout_freq_function);
        textInputLayoutStartFreq = findViewById(R.id.text_input_layout_start_freq);
        textInputLayoutEndFreq = findViewById(R.id.text_input_layout_end_freq);
        textInputLayoutFreqLength = findViewById(R.id.text_input_layout_freq_length);
        textInputLayoutMinFreq = findViewById(R.id.text_input_layout_min_freq);
        textInputLayoutMaxFreq = findViewById(R.id.text_input_layout_max_freq);

        autoCompleteTextViewFreqFunction = findViewById(R.id.auto_complete_text_view_freq_function);
        textInputEditTextStartFreq = findViewById(R.id.edit_text_start_freq);
        textInputEditTextEndFreq = findViewById(R.id.edit_text_end_freq);
        textInputEditTextFreqLength = findViewById(R.id.edit_text_freq_length);
        textInputEditTextMinFreq = findViewById(R.id.edit_text_min_freq);
        textInputEditTextMaxFreq = findViewById(R.id.edit_text_max_freq);

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
        textInputLayoutFreqFunction.requestFocus();
    }

    public void onButtonFreqMakerCreate(View view) {
        function = autoCompleteTextViewFreqFunction.getText().toString();
        String startFreqString = textInputEditTextStartFreq.getText().toString();
        if (!startFreqString.isEmpty()) {
            startFreq = Double.valueOf(startFreqString);
        }
        String endFreqString = textInputEditTextEndFreq.getText().toString();
        if (!endFreqString.isEmpty()) {
            endFreq = Double.valueOf(endFreqString);
        }
        String freqLengthString = textInputEditTextFreqLength.getText().toString();
        if (!freqLengthString.isEmpty()) {
            freqLength = Double.valueOf(freqLengthString);
        }
        String minFreqString = textInputEditTextMinFreq.getText().toString();
        if (!minFreqString.isEmpty()) {
            minFreq = Double.valueOf(minFreqString);
        }
        String maxFreqString = textInputEditTextMaxFreq.getText().toString();
        if (!maxFreqString.isEmpty()) {
            maxFreq = Double.valueOf(maxFreqString);
        }
        boolean mustReturn = false;
        if (startFreq == -1) {
            mustReturn = true;
            textInputLayoutStartFreq.setError("Frequency needed");
        }
        if (freqLength == -1) {
            mustReturn = true;
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

    public void onButtonFreqMakerCancel(View view) {
        finish();
    }

}
