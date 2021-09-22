package com.reitler.got.view;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import com.reitler.got.databinding.ActivityMatchSummaryDialogBinding;
import com.reitler.got.vm.MatchSummaryViewModel;

public class MatchSummaryDialogActivity extends FragmentActivity {

    private ActivityMatchSummaryDialogBinding binding;
    private MatchSummaryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMatchSummaryDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(MatchSummaryViewModel.class);
        getMainExecutor().execute(() -> viewModel.initOpenMatch());

        int width = ViewGroup.LayoutParams.MATCH_PARENT;
        int height = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setLayout(width, height);
    }

}