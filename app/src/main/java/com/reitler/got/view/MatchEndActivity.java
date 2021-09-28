package com.reitler.got.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.reitler.got.databinding.ActivityMatchEndBinding;
import com.reitler.got.view.statistics.StatisticsActivity;
import com.reitler.got.vm.MatchSummaryViewModel;


public class MatchEndActivity extends BaseActivity {

    public static String EXTRA_MATCH_ID = "com.reitler.got.view.MatchENdActivity.matchId";

    private MatchSummaryViewModel viewModel;
    private ActivityMatchEndBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityMatchEndBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(MatchSummaryViewModel.class);
        long matchID = getIntent().getLongExtra(EXTRA_MATCH_ID, -1l);
        if (matchID >= 0) {
            viewModel.init(matchID);
        }

        this.binding.buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMenu();
            }
        });

        this.binding.buttonRematch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rematch();
            }
        });

        this.binding.buttonStatistics.setOnClickListener(listener ->
                startActivity(new Intent(this, StatisticsActivity.class)));
    }

    @Override
    public void onBackPressed() {
        //
    }

    private void backToMenu() {
        Intent intent = new Intent(this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void rematch() {
        this.viewModel.rematch(() -> {
            getMainExecutor().execute(() -> {
                Intent intent = new Intent(this, MatchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        });
    }
}
