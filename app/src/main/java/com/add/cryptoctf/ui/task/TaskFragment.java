package com.add.cryptoctf.ui.task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.add.cryptoctf.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    private ArrayList<Task> tasks = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference tasksReference;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init();

        recycler(view);

    }

    public void recycler(View view){

        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new ExampleAdapter(tasks);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void init(){

        tasksReference = FirebaseDatabase.getInstance().getReference("Tasks");

        tasks.clear();

        tasksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){

            showData(snapshot);
        }

        public void showData(DataSnapshot dataSnapshot){

            for (DataSnapshot ds : dataSnapshot.getChildren()) {

                Task task = new Task(ds.child("title").getValue().toString(), ds.child("points").getValue().toString());
                tasks.add(task);
            }

        }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}

class Task {
    String title;
    String points;

    Task(String title, String points) {
        this.title = title;
        this.points = points;
    }

    public String getTitle() {
        return title;
    }

    public String getPoints() {
        return points;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPoints(String points) {
        this.points = points;
    }
}