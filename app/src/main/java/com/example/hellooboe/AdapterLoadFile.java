package com.example.hellooboe;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdapterLoadFile extends RecyclerView.Adapter<AdapterLoadFile.LoadFileItem> {

    private String[] fileNames;

    public AdapterLoadFile(String[] fileNames) {
        this.fileNames = fileNames;
    }

    @Override
    @NonNull
    public LoadFileItem onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textViewLoadFile = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.text_view_load_file, parent, false);
        return new LoadFileItem(textViewLoadFile);
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

        public LoadFileItem(TextView textViewFileName) {
            super(textViewFileName);
            this.textViewFileName = textViewFileName;
        }

    }

}

