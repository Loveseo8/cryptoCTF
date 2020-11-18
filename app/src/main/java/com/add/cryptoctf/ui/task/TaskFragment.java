package com.add.cryptoctf.ui.task;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TaskFragment extends Fragment {

    private List<Task> tasks = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private TaskAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference tasksReference;
    SharedPreferences sharedPreferencestitle;
    SharedPreferences sharedPreferencespoints;
    SharedPreferences sharedPreferenceanswer;
    SharedPreferences sharedPreferencetask;
    String ID_TITLE = "title";
    String ID_POINTS = "points";
    String ID_ANSWER = "answer";
    String ID_TASK = "task";


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferencestitle = getActivity().getSharedPreferences(ID_TITLE, Context.MODE_PRIVATE);
        sharedPreferencespoints = getActivity().getSharedPreferences(ID_POINTS, Context.MODE_PRIVATE);
        sharedPreferenceanswer = getActivity().getSharedPreferences(ID_POINTS, Context.MODE_PRIVATE);
        sharedPreferencetask = getActivity().getSharedPreferences(ID_POINTS, Context.MODE_PRIVATE);


        tasksReference = FirebaseDatabase.getInstance().getReference("Tasks");

        tasksReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                tasks.clear();

                for(DataSnapshot ds : snapshot.getChildren()){

                    String title = ds.child("title").getValue().toString();
                    String points = ds.child("points").getValue().toString();
                    String answer = ds.child("answer").getValue().toString();
                    String task = ds.child("task").getValue().toString();
                    SharedPreferences.Editor ed = sharedPreferencestitle.edit();
                    ed.putString(ID_TITLE, title);
                    ed.commit();

                    SharedPreferences.Editor eg = sharedPreferenceanswer.edit();
                    eg.putString(ID_ANSWER, title);
                    eg.commit();

                    SharedPreferences.Editor em = sharedPreferencetask.edit();
                    em.putString(ID_TASK, title);
                    em.commit();

                    SharedPreferences.Editor es = sharedPreferencespoints.edit();
                    es.putString(ID_POINTS, points);
                    es.commit();

                        tasks.add(new Task(sharedPreferencestitle.getString(ID_TITLE, ""), sharedPreferencespoints.getString(ID_POINTS, ""), sharedPreferencestitle.getString(ID_TASK, ""), sharedPreferencestitle.getString(ID_ANSWER, "")));

                }

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mRecyclerView = view.findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mAdapter = new TaskAdapter(tasks);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new TaskAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Task task1 = tasks.get(position);
                showTaskDialog(task1);
            }
        });
    }

    public void showTaskDialog(Task task){

        

    }
}

class Task {
    String title = "";
    String points = "";
    String task = "";
    String answer = "";

    Task(String title, String points, String task, String answer) {
        this.title = title;
        this.answer = answer;
        this.task = task;
        this.points = points;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
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