package com.add.cryptoctf.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.add.cryptoctf.R;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    private List<Task> tasks;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tasks = new ArrayList<>();
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));
        tasks.add(new Task("Line 1", "63"));





        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExampleAdapter(tasks);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
    }

class Task {
    String title;
    String points;

    Task(String title, String points) {
        this.title = title;
        this.points = points;
    }
}