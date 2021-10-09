package com.reitler.got.view;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.reitler.got.R;
import com.reitler.got.databinding.FragmentMatchSummaryBinding;
import com.reitler.got.databinding.ViewSummaryEntryBinding;
import com.reitler.got.model.ScoreDataUtil;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.Player;
import com.reitler.got.model.match.ScoreData;
import com.reitler.got.vm.MatchSummaryViewModel;

import java.util.Map;

public class MatchSummaryFragment extends Fragment {

    private FragmentMatchSummaryBinding binding;
    private MatchSummaryViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(MatchSummaryViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        this.binding = FragmentMatchSummaryBinding.inflate(getLayoutInflater(), container, false);

        setHeader(binding.summaryTable);

        viewModel.getMatch().observe(requireActivity(), new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                setData(match);
            }
        });
        return this.binding.getRoot();
    }

    private void setHeader(TableLayout table) {
        ViewSummaryEntryBinding header = ViewSummaryEntryBinding.inflate(getLayoutInflater(), table, false);

        header.summaryEntryName.setText(R.string.summary_table_header_name);
        header.summaryEntryName.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore1.setText(format(1));
        header.summaryEntryScore1.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore2.setText(format(2));
        header.summaryEntryScore2.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore3.setText(format(3));
        header.summaryEntryScore3.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore4.setText(format(4));
        header.summaryEntryScore4.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore5.setText(format(5));
        header.summaryEntryScore5.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore6.setText(format(6));
        header.summaryEntryScore6.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore7.setText(format(7));
        header.summaryEntryScore7.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore8.setText(format(8));
        header.summaryEntryScore8.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore9.setText(format(9));
        header.summaryEntryScore9.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore10.setText(format(10));
        header.summaryEntryScore10.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore11.setText(format(11));
        header.summaryEntryScore11.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScore12.setText(format(12));
        header.summaryEntryScore12.setTypeface(null, Typeface.BOLD);
        header.summaryEntryScoreSum.setText(R.string.summary_table_header_sum);
        header.summaryEntryScoreSum.setTypeface(null, Typeface.BOLD);

        int padding = getResources().getDimensionPixelSize(R.dimen.table_header_padding);
        header.getRoot().setPadding(padding, padding, padding, padding);

        table.addView(header.getRoot());
    }

    private void setData(Match match) {
        TableLayout summaryTable = binding.summaryTable;

        // first, remove all table rows that might be present already
        int childCount = summaryTable.getChildCount();
        for (int i = childCount - 1; i > 0; i--) {
            summaryTable.removeView(summaryTable.getChildAt(i));
        }

        // then set the new data in the table
        for (Map.Entry<Player, ScoreData> entry : match.getScoreDatas().entrySet()) {

            ViewSummaryEntryBinding entryBinding = ViewSummaryEntryBinding.inflate(getLayoutInflater(), summaryTable, false);
            entryBinding.summaryEntryName.setText(entry.getKey().getName());
            ScoreData scoreData = entry.getValue();

            formatCell(entryBinding.summaryEntryScore1, scoreData.get(1));
            formatCell(entryBinding.summaryEntryScore2, scoreData.get(2));
            formatCell(entryBinding.summaryEntryScore3, scoreData.get(3));
            formatCell(entryBinding.summaryEntryScore4, scoreData.get(4));
            formatCell(entryBinding.summaryEntryScore5, scoreData.get(5));
            formatCell(entryBinding.summaryEntryScore6, scoreData.get(6));
            formatCell(entryBinding.summaryEntryScore7, scoreData.get(7));
            formatCell(entryBinding.summaryEntryScore8, scoreData.get(8));
            formatCell(entryBinding.summaryEntryScore9, scoreData.get(9));
            formatCell(entryBinding.summaryEntryScore10, scoreData.get(10));
            formatCell(entryBinding.summaryEntryScore11, scoreData.get(11));
            formatCell(entryBinding.summaryEntryScore12, scoreData.get(12));

            int remainingScore = ScoreDataUtil.getRemainingScore(scoreData);
            entryBinding.summaryEntryScoreSum.setText(format(remainingScore));
            if (remainingScore == 0) {
                entryBinding.summaryEntryScoreSum.setBackgroundColor(requireActivity().getColor(R.color.green));
            }
            summaryTable.addView(entryBinding.getRoot());
        }
    }


    private void formatCell(TextView cell, int score) {
        cell.setText(format(score));
        if (score == 5) {
            cell.setBackgroundColor(requireActivity().getColor(R.color.green));
        }
    }

    private String format(int score) {
        return String.format("%3d", score);
    }

}
