package com.example.hellooboe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class LoadFileActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_screen);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.load_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        String[] myDataset = {"a", "b", "c", "d", "e"};

        // specify an adapter (see also next example)
        mAdapter = new LoadFileAdapter(myDataset);
        recyclerView.setAdapter(mAdapter);
    }
}