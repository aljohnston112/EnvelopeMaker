package com.example.hellooboe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityLoadFile extends AppCompatActivity {

    private RecyclerView recyclerViewLoadFile;
    private RecyclerView.Adapter recylerViewAdapterLoadFile;
    private RecyclerView.LayoutManager recylerViewLayoutManagerLoadFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.load_screen);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        recyclerViewLoadFile = (RecyclerView) findViewById(R.id.recycler_view_load_file);
        recylerViewLayoutManagerLoadFile = new LinearLayoutManager(this);
        recyclerViewLoadFile.setLayoutManager(recylerViewLayoutManagerLoadFile);
        //TODO Load strings from c++ SaveFiles
        String[] myDataset = {"a", "b", "c", "d", "e"};
        recylerViewAdapterLoadFile = new AdapterLoadFile(myDataset);
        recyclerViewLoadFile.setAdapter(recylerViewAdapterLoadFile);
    }

}