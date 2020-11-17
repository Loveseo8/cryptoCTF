package com.add.cryptoctf.ui.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.add.cryptoctf.R;

import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {

    private List<Task> tasks;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder {

    public TextView titlel;
    public TextView pointss;

    public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
        super(itemView);
        titlel = (TextView) itemView.findViewById(R.id.title);
        pointss = (TextView) itemView.findViewById(R.id.points);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
}
    public ExampleAdapter(List<Task> tasks) {
        this.tasks = tasks;
    }
    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
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
