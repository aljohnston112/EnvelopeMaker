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

public class ActivityAmpMaker extends AppCompatActivity {

    private int col;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amp_maker);
        TextInputLayout textInputLayoutAmp = findViewById(R.id.text_input_layout_amp_function);
        ArrayAdapter<CharSequence> arrayAdapterAmp = ArrayAdapter.createFromResource(this,
                R.array.functions, R.layout.auto_complete_text_view_function);
        AutoCompleteTextView autoCompleteTextViewAmpFunction = (AutoCompleteTextView) textInputLayoutAmp.getEditText();
        assert autoCompleteTextViewAmpFunction != null;
        autoCompleteTextViewAmpFunction.setAdapter(arrayAdapterAmp);
        textInputLayoutAmp.requestFocus();
        col = getIntent().getIntExtra(ViewFunction.COL_NUMBER_KEY, -1);
        ConstraintLayout constraintLayoutAmp = findViewById(R.id.constraint_layout_amp);
        final ViewTreeObserver obs = constraintLayoutAmp.getViewTreeObserver();
        obs.addOnPreDrawListener(() -> {
            Button buttonCreate = findViewById(R.id.button_create_amp);
            Button buttonCancel = findViewById(R.id.button_cancel_amp);
            TextInputLayout textInputLayoutStartAmp = findViewById(R.id.text_input_layout_start_amp);
            ViewGroup.LayoutParams params = buttonCreate.getLayoutParams();
            params.height = textInputLayoutStartAmp.getHeight();
            buttonCreate.requestLayout();
            ViewGroup.LayoutParams params2 = buttonCancel.getLayoutParams();
            params2.height = textInputLayoutStartAmp.getHeight();
            buttonCancel.requestLayout();
            //We only care the first time it happens, so remove it
            //Post your animation here, then return true
            return true;
        });
    }

    public void onButtonAmpMakerCreate(View view) {
        AutoCompleteTextView autoCompleteTextViewAmpFunction = findViewById(R.id.auto_complete_text_view_amp_function);
        String function = autoCompleteTextViewAmpFunction.getText().toString();
        TextInputEditText textInputEditTextStartAmp = findViewById(R.id.text_input_edit_start_amp);
        TextInputEditText textInputEditTextEndAmp = findViewById(R.id.edit_text_end_amp);
        TextInputEditText textInputEditTextAmpLength = findViewById(R.id.edit_text_amp_length);
        TextInputEditText textInputEditTextMinAmp = findViewById(R.id.edit_text_min_amp);
        TextInputEditText textInputEditTextMaxAmp = findViewById(R.id.edit_text_max_amp);
        String startAmpString = textInputEditTextStartAmp.getText().toString();
        double startAmp = -1;
        if (!startAmpString.isEmpty()) {
            startAmp = Double.valueOf(startAmpString);
        }
        String endAmpString = textInputEditTextEndAmp.getText().toString();
        double endAmp = -1;
        if (!endAmpString.isEmpty()) {
            endAmp = Double.valueOf(endAmpString);
        }
        String ampLengthString = textInputEditTextAmpLength.getText().toString();
        double ampLength = -1;
        if (!ampLengthString.isEmpty()) {
            ampLength = Double.valueOf(ampLengthString);
        }
        String minAmpString = textInputEditTextMinAmp.getText().toString();
        double minAmp = -1;
        if (!minAmpString.isEmpty()) {
            minAmp = Double.valueOf(minAmpString);
        }
        String maxAmpString = textInputEditTextMaxAmp.getText().toString();
        double maxAmp = -1;
        if (!maxAmpString.isEmpty()) {
            maxAmp = Double.valueOf(maxAmpString);
        }

        boolean mustReturn = false;
        if (startAmp == -1) {
            mustReturn = true;
            TextInputLayout textInputLayoutStartAmp = findViewById(R.id.text_input_layout_start_amp);
            textInputLayoutStartAmp.setError("Positive amplitude needed");
        }

        if (ampLength == -1) {
            mustReturn = true;
            TextInputLayout textInputLayoutStartAmp = findViewById(R.id.text_input_layout_amp_length);
            textInputLayoutStartAmp.setError("Positive length needed");
        }

        if (mustReturn) {
            return;
        }

        if (function.contentEquals(getResources().getString(R.string.Constant))) {
            float[] data = NativeMethods.loadConstant(startAmp, ampLength, 0, col);
            double min = NativeMethods.getMinAmp();
            double max = NativeMethods.getMaxAmp();
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

    public void onButtonFAmpMakerCancel(View view) {
        finish();
    }

}