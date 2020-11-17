package com.add.cryptoctf.ui.task;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.add.cryptoctf.R;

import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

private List<Task> tasks;

public static class ExampleViewHolder extends RecyclerView.ViewHolder {

    public TextView titlel;
    public TextView pointss;

    public ExampleViewHolder(View itemView) {
        super(itemView);
        titlel = (TextView) itemView.findViewById(R.id.title);
        pointss = (TextView) itemView.findViewById(R.id.points);
    }
}
    public ExampleAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v);
        return evh;
    }
    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        Task currentItem = tasks.get(position);
        holder.titlel.setText(currentItem.title);
        holder.pointss.setText(currentItem.points);
    }
    @Override
    public int getItemCount() {
        return tasks.size();
    }
}
