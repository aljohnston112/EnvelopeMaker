package com.example.hellooboe;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

public class ActivityFreqMaker extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_freq_maker);
        TextInputLayout textInputLayoutAmpFunction = (TextInputLayout) findViewById(R.id.text_input_layout_freq_function);
        ArrayAdapter<CharSequence> arrayAdapterFreq = ArrayAdapter.createFromResource(this,
                R.array.functions, R.layout.auto_complete_text_view_function);
        AutoCompleteTextView autoCompleteTextViewAmpFunction = (AutoCompleteTextView) textInputLayoutAmpFunction.getEditText();
        assert autoCompleteTextViewAmpFunction != null;
        autoCompleteTextViewAmpFunction.setAdapter(arrayAdapterFreq);
        textInputLayoutAmpFunction.requestFocus();
    }
}

