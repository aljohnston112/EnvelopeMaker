package com.example.hellooboe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityLoadFile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView recyclerViewLoadFile = (RecyclerView) findViewById(R.id.recycler_view_load_file);
        RecyclerView.LayoutManager linearLayoutManagerLoadFile = new LinearLayoutManager(this);
        recyclerViewLoadFile.setLayoutManager(linearLayoutManagerLoadFile);
        //TODO Load strings from c++ SaveFiles
        String[] saveFileNames = {"a", "b", "c", "d", "e"};
        RecyclerView.Adapter recyclerViewAdapterLoadFile = new AdapterLoadFile(saveFileNames);
        recyclerViewLoadFile.setAdapter(recyclerViewAdapterLoadFile);
    }

}