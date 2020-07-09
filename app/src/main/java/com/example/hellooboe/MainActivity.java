package com.example.hellooboe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

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
        ampRecyclerView = (RecyclerView) findViewById(R.id.AmpRecyclerView);
        freqRecyclerView = (RecyclerView) findViewById(R.id.FreqRecyclerView);

        // use a linear layout manager
        ampLayoutManager = new LinearLayoutManager(this);
        freqLayoutManager = new LinearLayoutManager(this);

        ampRecyclerView.setLayoutManager(ampLayoutManager);
        freqRecyclerView.setLayoutManager(freqLayoutManager);

        myDataset.add(new FunctionView(this));

        // specify an adapter (see also next example)
        ampAdapter = new MainActivityAdapter(myDataset);
        ampRecyclerView.setAdapter(ampAdapter);

        freqAdapter = new MainActivityAdapter(myDataset);
        freqRecyclerView.setAdapter(freqAdapter);

    }
}