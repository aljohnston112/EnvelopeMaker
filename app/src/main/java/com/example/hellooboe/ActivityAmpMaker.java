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

public class ActivityAmpMaker extends AppCompatActivity {

    private int col;

    ConstraintLayout constraintLayoutAmp;

    TextInputLayout textInputLayoutAmpFunction;
    TextInputLayout textInputLayoutStartAmp;
    TextInputLayout textInputLayoutEndAmp;
    TextInputLayout textInputLayoutAmpLength;
    TextInputLayout textInputLayoutMinAmp;
    TextInputLayout textInputLayoutMaxAmp;
    TextInputLayout textInputLayoutAmpCycles;

    AutoCompleteTextView autoCompleteTextViewAmpFunction;
    TextInputEditText textInputEditStartAmp;
    TextInputEditText textInputEditEndAmp;
    TextInputEditText textInputEditAmpLength;
    TextInputEditText textInputEditMinAmp;
    TextInputEditText textInputEditMaxAmp;
    TextInputEditText textInputEditAmpCycles;


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
        setContentView(R.layout.activity_amp_maker);
        findViews();
        setUpFunctionAutoComplete();
        resizeButtons();
        textInputLayoutStartAmp.setVisibility(View.INVISIBLE);
        textInputLayoutEndAmp.setVisibility(View.INVISIBLE);
        textInputLayoutAmpLength.setVisibility(View.INVISIBLE);
        textInputLayoutMinAmp.setVisibility(View.INVISIBLE);
        textInputLayoutMaxAmp.setVisibility(View.INVISIBLE);
        textInputLayoutAmpCycles.setVisibility(View.INVISIBLE);
        populate();
        col = getIntent().getIntExtra(ViewFunction.COL_NUMBER_KEY, -1);
        width = getIntent().getIntExtra(ViewFunction.WIDTH_DATA, -1);

    }

    private void populate() {
        function = getIntent().getStringExtra(ViewFunction.FUNCTION_DATA);
        if (function != null) {
            autoCompleteTextViewAmpFunction.setText(function);
            autoCompleteTextViewAmpFunction.performCompletion();
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
            textInputEditStartAmp.setText(String.valueOf(start*100));
        }
        end = getIntent().getDoubleExtra(ViewFunction.END_DATA, -1);
        if (end != -1) {
            textInputEditEndAmp.setText(String.valueOf(end));
        }
        length = getIntent().getDoubleExtra(ViewFunction.LENGTH_DATA, -1);
        if (length != -1) {
            textInputEditAmpLength.setText(String.valueOf(length));
        }
        cycles = getIntent().getDoubleExtra(ViewFunction.CYCLES_DATA, -1);
        if (cycles != -1) {
            textInputEditAmpCycles.setText(String.valueOf(cycles));
        }
        min = getIntent().getDoubleExtra(ViewFunction.MIN_DATA, -1);
        if (min != -1) {
            textInputEditMinAmp.setText(String.valueOf(min));
        }
        max = getIntent().getDoubleExtra(ViewFunction.MAX_DATA, -1);
        if (max != -1) {
            textInputEditMaxAmp.setText(String.valueOf(max));
        }
    }

    private void setUpConstant(){
        textInputLayoutStartAmp.setVisibility(View.VISIBLE);
        textInputLayoutEndAmp.setVisibility(View.INVISIBLE);
        textInputLayoutAmpLength.setVisibility(View.VISIBLE);
        textInputLayoutMinAmp.setVisibility(View.INVISIBLE);
        textInputLayoutMaxAmp.setVisibility(View.INVISIBLE);
        textInputLayoutAmpCycles.setVisibility(View.INVISIBLE);
    }

    private void findViews() {
        constraintLayoutAmp = findViewById(R.id.constraint_layout_amp);

        textInputLayoutAmpFunction = findViewById(R.id.text_input_layout_amp_function);
        textInputLayoutStartAmp = findViewById(R.id.text_input_layout_start_amp);
        textInputLayoutEndAmp = findViewById(R.id.text_input_layout_end_amp);
        textInputLayoutAmpLength = findViewById(R.id.text_input_layout_amp_length);
        textInputLayoutMinAmp = findViewById(R.id.text_input_layout_min_amp);
        textInputLayoutMaxAmp = findViewById(R.id.text_input_layout_max_amp);
        textInputLayoutAmpCycles = findViewById(R.id.text_input_layout_amp_cycles);

        autoCompleteTextViewAmpFunction = findViewById(R.id.auto_complete_text_view_amp_function);
        textInputEditStartAmp = findViewById(R.id.text_input_edit_start_amp);
        textInputEditEndAmp = findViewById(R.id.edit_text_end_amp);
        textInputEditAmpLength = findViewById(R.id.edit_text_amp_length);
        textInputEditMinAmp = findViewById(R.id.edit_text_min_amp);
        textInputEditMaxAmp = findViewById(R.id.edit_text_max_amp);
        textInputEditAmpCycles = findViewById(R.id.edit_text_amp_cycles);

        buttonCreate = findViewById(R.id.button_create_amp);
        buttonCancel = findViewById(R.id.button_cancel_amp);
    }

    private void resizeButtons() {
        final ViewTreeObserver obs = constraintLayoutAmp.getViewTreeObserver();
        obs.addOnPreDrawListener(() -> {
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

    private void setUpFunctionAutoComplete() {
        ArrayAdapter<CharSequence> arrayAdapterAmp = ArrayAdapter.createFromResource(this,
                R.array.functions, R.layout.auto_complete_text_view_function);
        AutoCompleteTextView autoCompleteTextViewAmpFunction = (AutoCompleteTextView) textInputLayoutAmpFunction.getEditText();
        assert autoCompleteTextViewAmpFunction != null;
        autoCompleteTextViewAmpFunction.setAdapter(arrayAdapterAmp);
        autoCompleteTextViewAmpFunction.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(arrayAdapterAmp.getItem(i).toString().contentEquals(getResources().getString(R.string.Constant))){
                    textInputLayoutStartAmp.setVisibility(View.VISIBLE);
                    textInputLayoutEndAmp.setVisibility(View.INVISIBLE);
                    textInputLayoutAmpLength.setVisibility(View.VISIBLE);
                    textInputLayoutMinAmp.setVisibility(View.INVISIBLE);
                    textInputLayoutMaxAmp.setVisibility(View.INVISIBLE);
                    textInputLayoutAmpCycles.setVisibility(View.INVISIBLE);
                }
            }
        });
        textInputLayoutAmpFunction.requestFocus();
    }

    public void onButtonAmpMakerCreate(View view) {
        function = autoCompleteTextViewAmpFunction.getText().toString();
        if(function == null || function.isEmpty()){
            textInputLayoutAmpFunction.setError("Function needed");
            return;
        }

        String startAmpString = Objects.requireNonNull(textInputEditStartAmp.getText()).toString();
        if (!startAmpString.isEmpty()) {
            start = Double.parseDouble(startAmpString);
        }

        String endAmpString = Objects.requireNonNull(textInputEditEndAmp.getText()).toString();
        if (!endAmpString.isEmpty()) {
            end = Double.parseDouble(endAmpString);
        }

        String ampLengthString = Objects.requireNonNull(textInputEditAmpLength.getText()).toString();
        if (!ampLengthString.isEmpty()) {
            length = Double.parseDouble(ampLengthString);

        }

        String ampCyclesString = Objects.requireNonNull(textInputEditAmpCycles.getText()).toString();
        if (!ampCyclesString.isEmpty()) {
            cycles = Double.parseDouble(ampCyclesString);
        }

        String minAmpString = Objects.requireNonNull(textInputEditMinAmp.getText()).toString();
        if (!minAmpString.isEmpty()) {
            min = Double.parseDouble(minAmpString);
        }

        String maxAmpString = Objects.requireNonNull(textInputEditMaxAmp.getText()).toString();
        if (!maxAmpString.isEmpty()) {
            max = Double.parseDouble(maxAmpString);
        }

        float[] data = null;
        boolean mustReturn = false;
        if (function.contentEquals(getResources().getString(R.string.Constant))) {
            if (start == -1) {
                mustReturn = true;
                textInputLayoutStartAmp.setError("Positive amplitude needed");
            } else if (start > 100) {
                mustReturn = true;
                textInputLayoutStartAmp.setError("Amplitude must be 100 or below");
            } else if (start < 0) {
                textInputLayoutStartAmp.setError("Amplitude must be 0 or above");
            } else {
                start /= 100.0;
            }
            if (length <= 0) {
                mustReturn = true;
                textInputLayoutAmpLength.setError("Positive length needed");
            }
            if (mustReturn) {
                return;
            }
            data = NativeMethods.loadConstant(start, length, 0, col, width);

        } else if (function.contentEquals(getResources().getString(R.string.Exponential))) {

        } else if (function.contentEquals(getResources().getString(R.string.Linear))) {

        } else if (function.contentEquals(getResources().getString(R.string.Logarithm))) {

        } else if (function.contentEquals(getResources().getString(R.string.Nth_Root))) {

        } else if (function.contentEquals(getResources().getString(R.string.Power))) {

        } else if (function.contentEquals(getResources().getString(R.string.Quadratic))) {

        } else if (function.contentEquals(getResources().getString(R.string.Sine))) {

        }
        double min = NativeMethods.getMinAmp();
        double max = NativeMethods.getMaxAmp();
        Intent intent = new Intent();
        intent.putExtra(ViewFunction.FLOAT_DATA, data);
        intent.putExtra(ViewFunction.MIN_Y_DATA, min);
        intent.putExtra(ViewFunction.MAX_Y_DATA, max);
        intent.putExtra(ViewFunction.COL_DATA, col);
        intent.putExtra(ViewFunction.FUNCTION_DATA, function);
        intent.putExtra(ViewFunction.START_DATA, start);
        intent.putExtra(ViewFunction.LENGTH_DATA, length);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    public void onButtonAmpMakerCancel(View view) {
        finish();
    }

}