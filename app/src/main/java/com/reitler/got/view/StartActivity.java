package com.reitler.got.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.reitler.got.databinding.ActivityStartBinding;
import com.reitler.got.model.match.Match;
import com.reitler.got.view.statistics.StatisticsActivity;
import com.reitler.got.vm.StartViewModel;

public class StartActivity extends BaseActivity {

    private ActivityStartBinding binding;
    private StartViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        this.viewModel = new ViewModelProvider(this).get(StartViewModel.class);

        binding.buttonCreateNewGame.setOnClickListener(listener ->
                startActivity(new Intent(StartActivity.this, CreateMatchActivity.class)));
        binding.buttonLoadGame.setOnClickListener(listener ->
                startActivity(new Intent(StartActivity.this, MatchActivity.class)));
        binding.buttonStatistics.setOnClickListener(listener ->
                startActivity(new Intent(StartActivity.this, StatisticsActivity.class)));

        this.viewModel.getOpenMatch().observe(this, new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                binding.buttonLoadGame.setEnabled(match != null);
            }
        });
    }
}