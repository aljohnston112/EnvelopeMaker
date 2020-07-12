package com.example.hellooboe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<FunctionView> myDataset = new ArrayList<FunctionView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.llrows);
        linearLayout.removeViewAt(1);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        linearLayout.addView(layoutInflater.inflate(R.layout.function_holder, null), 1);
        ((LinearLayout)linearLayout.getChildAt(1)).removeViewAt(1);
    }
}