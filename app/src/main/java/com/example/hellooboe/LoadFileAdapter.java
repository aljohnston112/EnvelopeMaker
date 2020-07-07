package com.example.hellooboe;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class LoadFileAdapter extends RecyclerView.Adapter<LoadFileAdapter.LoadFileHolder> {

    private String[] files;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class LoadFileHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public TextView textView;

        public LoadFileHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public LoadFileAdapter(String[] myDataset) {
        files = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LoadFileHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
        // create a new view
        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.file_holder, parent, false);
        LoadFileHolder vh = new LoadFileHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(LoadFileHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.textView.setText(files[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return files.length;
    }


}

