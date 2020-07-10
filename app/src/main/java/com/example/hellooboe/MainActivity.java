package com.example.hellooboe;

import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView ampRecyclerView;
    private RecyclerView.Adapter ampAdapter;
    private RecyclerView.LayoutManager ampLayoutManager;

    private RecyclerView freqRecyclerView;
    private RecyclerView.Adapter freqAdapter;
    private RecyclerView.LayoutManager freqLayoutManager;

    List<FunctionView> myDataset = new ArrayList<FunctionView>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        LinearLayout linearLayout = findViewById(R.id.main_ll);
        linearLayout.setWillNotDraw(false);

        ampRecyclerView = (RecyclerView) findViewById(R.id.AmpRecyclerView);
        freqRecyclerView = (RecyclerView) findViewById(R.id.FreqRecyclerView);
        ampRecyclerView.setWillNotDraw(false);
        freqRecyclerView.setWillNotDraw(false);

        // use a linear layout manager
        ampLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        freqLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        ampRecyclerView.setLayoutManager(ampLayoutManager);
        freqRecyclerView.setLayoutManager(freqLayoutManager);

        myDataset.add(new FunctionView(this));
        myDataset.add(new FunctionView(this));
        myDataset.add(new FunctionView(this));

        // specify an adapter
        ampAdapter = new MainActivityAdapter(myDataset);
        ampRecyclerView.setAdapter(ampAdapter);

        freqAdapter = new MainActivityAdapter(myDataset);
        freqRecyclerView.setAdapter(freqAdapter);

    }
}