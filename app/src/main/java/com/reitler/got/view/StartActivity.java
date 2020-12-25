package com.reitler.got.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.reitler.got.GotApplication;
import com.reitler.got.R;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.MatchDataManager;
import com.reitler.got.vm.MatchViewModel;
import com.reitler.got.vm.StartViewModel;

public class StartActivity extends AppCompatActivity {

    private StartViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        this.viewModel = new ViewModelProvider(this).get(StartViewModel.class);

        Button bCreateGame = findViewById(R.id.button_create_new_game);
        Button bLoadGame = findViewById(R.id.button_load_game);

        bCreateGame.setOnClickListener(listener -> startActivity(new Intent(StartActivity.this, CreateGameActivity.class)));

        this.viewModel.getOpenMatch().observe(this, new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                bLoadGame.setEnabled(match != null);
            }
        });
        bLoadGame.setOnClickListener(listener ->
        {
            startActivity(new Intent(StartActivity.this, MainGameActivity.class));
        });
    }
}