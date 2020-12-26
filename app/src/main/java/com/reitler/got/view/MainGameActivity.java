package com.reitler.got.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.reitler.got.R;

public class MainGameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_game);
    }

    @Override
    public void onBackPressed() {
        // don't allow to go back
    }
}