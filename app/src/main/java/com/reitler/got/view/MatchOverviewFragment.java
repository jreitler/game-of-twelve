package com.reitler.got.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitler.got.databinding.FragmentMatchOverviewBinding;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.Player;
import com.reitler.got.vm.MatchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchOverviewFragment extends Fragment {
    List<OverviewListItem> overviewList = new ArrayList<>();
    private FragmentMatchOverviewBinding binding;
    private MatchViewModel viewModel;
    private OverviewListAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(MatchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentMatchOverviewBinding.inflate(getLayoutInflater(), container, false);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.adapter = new OverviewListAdapter(overviewList);
        RecyclerView recyclerView = this.binding.recyclerOverview;
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);
        this.viewModel.getMatch().observe(getViewLifecycleOwner(), new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                if (match != null) {
                    setData();
                }
            }
        });
        this.binding.overviewTitle.setOnClickListener(v -> {
            requireActivity().getMainExecutor().execute(() ->
                    startActivity(new Intent(requireActivity(), MatchSummaryDialogActivity.class)));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setData() {
        if (this.adapter != null) {
            overviewList.clear();

            int i = 0;
            for (Map.Entry<Player, ? extends LiveData<Integer>> entry : viewModel.getRemainingScores().entrySet()) {

                overviewList.add(new OverviewListItem(entry.getKey(), entry.getValue().getValue()));

                int finalI = i;
                entry.getValue().observe(getViewLifecycleOwner(), new Observer<Integer>() {
                    @Override
                    public void onChanged(Integer integer) {
                        valueChanged(finalI, integer.intValue());
                    }
                });
                i++;
            }
            this.adapter.setData(overviewList);
            this.adapter.notifyDataSetChanged();
        }
    }

    private void valueChanged(int pos, int newVal) {
        if (this.adapter != null) {
            this.adapter.setValue(pos, newVal);
            this.adapter.notifyItemChanged(pos);
        }
    }

}