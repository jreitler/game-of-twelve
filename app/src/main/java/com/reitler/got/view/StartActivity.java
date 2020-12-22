package com.reitler.got.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.reitler.got.R;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Button bCreateGame = findViewById(R.id.button_create_new_game);
        Button bLoadGame = findViewById(R.id.button_load_game);

        bCreateGame.setOnClickListener(listener -> startActivity(new Intent(StartActivity.this, CreateGameActivity.class)));
        bLoadGame.setOnClickListener(listener -> startActivity(new Intent(StartActivity.this, MainGameActivity.class)));
    }
}