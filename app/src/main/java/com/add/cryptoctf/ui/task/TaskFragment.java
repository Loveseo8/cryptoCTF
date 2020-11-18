package com.add.cryptoctf.ui.task;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.add.cryptoctf.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    String ID_userscore = "userscore";
    SharedPreferences sharedPreferencesuser;
    String oldscore;
    DatabaseReference userReference;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferencestitle = getActivity().getSharedPreferences(ID_TITLE, Context.MODE_PRIVATE);
        sharedPreferencespoints = getActivity().getSharedPreferences(ID_POINTS, Context.MODE_PRIVATE);
        sharedPreferenceanswer = getActivity().getSharedPreferences(ID_ANSWER, Context.MODE_PRIVATE);
        sharedPreferencetask = getActivity().getSharedPreferences(ID_TASK, Context.MODE_PRIVATE);
        sharedPreferencesuser = getActivity().getSharedPreferences(ID_userscore, Context.MODE_PRIVATE);

        userReference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid()).child("score");

        if(sharedPreferencesuser.contains(ID_userscore)){

            oldscore = sharedPreferencesuser.getString(ID_userscore, "");

        }


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
                    eg.putString(ID_ANSWER, answer);
                    eg.commit();

                    SharedPreferences.Editor em = sharedPreferencetask.edit();
                    em.putString(ID_TASK, task);
                    em.commit();

                    SharedPreferences.Editor es = sharedPreferencespoints.edit();
                    es.putString(ID_POINTS, points);
                    es.commit();

                        tasks.add(new Task(sharedPreferencestitle.getString(ID_TITLE, ""), sharedPreferencespoints.getString(ID_POINTS, ""), sharedPreferencetask.getString(ID_TASK, ""), sharedPreferenceanswer.getString(ID_ANSWER, "")));

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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
        LayoutInflater layoutInflater = getLayoutInflater();
        final View dialogView = layoutInflater.inflate(R.layout.task_dialog, null);
        dialogBuilder.setView(dialogView);

        final EditText entertask = dialogView.findViewById(R.id.enterflag);
        final TextView tasktext = dialogView.findViewById(R.id.task_text);
        final Button submitflag = dialogView.findViewById(R.id.submit);

        tasktext.setText(task.getTask());

        dialogBuilder.setTitle(task.getTitle());
        final AlertDialog b = dialogBuilder.create();
        b.show();

        submitflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String text_word = entertask.getText().toString().trim();

                if (TextUtils.isEmpty(text_word)) {

                    entertask.setError("Enter flag!");
                    entertask.requestFocus();

                } else {

                    if(text_word.equals(task.getAnswer())){

                        userReference.setValue(Integer.parseInt(oldscore) + Integer.parseInt(task.getPoints()));

                    }
                }

                b.dismiss();

            }

        });

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