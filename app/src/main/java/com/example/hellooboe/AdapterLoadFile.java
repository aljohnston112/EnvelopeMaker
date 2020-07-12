package com.example.hellooboe;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class AdapterLoadFile extends RecyclerView.Adapter<AdapterLoadFile.LoadFileItem> {

    private String[] fileNames;

    public AdapterLoadFile(String[] fileNames) {
        this.fileNames = fileNames;
    }

    @Override
    public LoadFileItem onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textViewLoadFile = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.load_file_item, parent, false);
        LoadFileItem loadFileItem = new LoadFileItem(textViewLoadFile);
        return loadFileItem;
    }

    @Override
    public void onBindViewHolder(LoadFileItem holder, int position) {
        holder.textViewFileName.setText(fileNames[position]);
    }

    @Override
    public int getItemCount() {
        return fileNames.length;
    }

    public static class LoadFileItem extends RecyclerView.ViewHolder {

        public TextView textViewFileName;

        public LoadFileItem(TextView textView) {
            super(textView);
            textViewFileName = textView;
        }

    }


}

