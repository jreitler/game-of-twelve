package com.reitler.got.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.reitler.got.R;

import java.util.ArrayList;
import java.util.List;

public class CreateGameActivity extends AppCompatActivity {

    List<UserViewItem> userList;

    public CreateGameActivity(){
        userList = new ArrayList<>();
        userList.add(new UserViewItem("Spieler 1"));
        userList.add(new UserViewItem("Spieler 2"));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_game);
        Button bStartGame = findViewById(R.id.button_start_game);
        Button bCreateNewUser = findViewById(R.id.button_create_user);
        bStartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(CreateGameActivity.this, MainGameActivity.class));
            }
        });
        bCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: Add new User and add new User to Recyclerview
            }
        });

        UserListAdapter adapter = new UserListAdapter(userList);
        RecyclerView userRecyclerView = findViewById(R.id.recyclerview_users);
        userRecyclerView.setAdapter(adapter);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}