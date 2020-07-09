package com.example.hellooboe;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.FunctionViewHolder> {

    private List<FunctionView> views = new ArrayList<FunctionView>();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class FunctionViewHolder extends RecyclerView.ViewHolder {

        // each data item is just a string in this case
        public FunctionView functionView;

        public FunctionViewHolder(FunctionView v) {
            super(v);
            functionView = v;
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MainActivityAdapter(List<FunctionView> myDataset) {
        views = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MainActivityAdapter.FunctionViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        // create a new view
        FunctionView v = (FunctionView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.function_holder, parent, false);
        MainActivityAdapter.FunctionViewHolder vh = new MainActivityAdapter.FunctionViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MainActivityAdapter.FunctionViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
    //TODO set the data
        holder.functionView = views.get(position);
        holder.functionView.invalidate();
        holder.functionView.requestLayout();
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return views.size();
    }

}
