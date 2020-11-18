package com.add.cryptoctf.ui.scoreboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class ScoreboardFragment extends Fragment {

    private List<User> users = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference userReference;
    SharedPreferences sharedPreferencescore;
    SharedPreferences sharedPreferencenickname;
    SharedPreferences sharedPreferenceuserscore;
    String ID_NICKNAME = "nickname";
    String ID_score = "score";
    String ID_userscore = "userscore";
    DatabaseReference userScore;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    TextView uscore;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scoreboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferencescore = getActivity().getSharedPreferences(ID_score, Context.MODE_PRIVATE);
        sharedPreferencenickname = getActivity().getSharedPreferences(ID_NICKNAME, Context.MODE_PRIVATE);
        sharedPreferenceuserscore = getActivity().getSharedPreferences(ID_userscore, Context.MODE_PRIVATE);
        uscore = view.findViewById(R.id.yourscore);

        userReference = FirebaseDatabase.getInstance().getReference("Users");


        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                users.clear();

                String iscore = snapshot.child(user.getUid()).child("score").getValue().toString();

                SharedPreferences.Editor ex = sharedPreferenceuserscore.edit();
                ex.putString(ID_userscore, iscore);
                ex.commit();

                uscore.setText("your score is " + sharedPreferenceuserscore.getString(ID_userscore, "") + " points");

                for(DataSnapshot ds : snapshot.getChildren()){

                    String nickname = ds.child("name").getValue().toString();
                    String score = ds.child("score").getValue().toString();
                    SharedPreferences.Editor ed = sharedPreferencenickname.edit();
                    ed.putString(ID_NICKNAME, nickname);
                    ed.commit();

                    SharedPreferences.Editor es = sharedPreferencescore.edit();
                    es.putString(ID_score, score);
                    es.commit();

                    users.add(new User(sharedPreferencescore.getString(ID_score, ""), sharedPreferencenickname.getString(ID_NICKNAME, "")));

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
        mAdapter = new ScoreboardAdapter(users);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}

class User {

    String score = "";
    String nickname = "";

    public User(String score, String nickname) {
        this.score = score;
        this.nickname = nickname;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}