package com.reitler.got.view;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.reitler.got.R;
import com.reitler.got.databinding.ActivityMatchSummaryBinding;
import com.reitler.got.databinding.ViewSummaryEntryBinding;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.Player;
import com.reitler.got.model.match.ScoreData;
import com.reitler.got.vm.MatchSummaryViewModel;

import java.util.Map;


public class MatchSummaryActivity extends AppCompatActivity {

    public static String EXTRA_MATCH_ID = "com.reitler.got.view.MatchSummaryActivity.matchId";

    private MatchSummaryViewModel viewModel;
    private ActivityMatchSummaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.binding = ActivityMatchSummaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setHeader(binding.summaryTable);
        viewModel = new ViewModelProvider(this).get(MatchSummaryViewModel.class);
        long matchID = getIntent().getLongExtra(EXTRA_MATCH_ID, -1l);
        if (matchID >= 0) {
            viewModel.init(matchID);
        }

        viewModel.getMatch().observe(this, new Observer<Match>() {
            @Override
            public void onChanged(Match match) {
                setData(match);
            }
        });

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
    }

    @Override
    public void onBackPressed() {
        //
    }

    private void backToMenu(){
        Intent intent = new Intent(this, StartActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void rematch(){
        this.viewModel.rematch(() -> {
            getMainExecutor().execute( () -> {
                Intent intent = new Intent(this, MatchActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            });
        });
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

            int remainingScore = scoreData.remainingScore();
            entryBinding.summaryEntryScoreSum.setText(format(remainingScore));
            if (remainingScore == 0) {
                entryBinding.summaryEntryScoreSum.setBackgroundColor(getColor(R.color.green));
            }
            summaryTable.addView(entryBinding.getRoot());
        }
    }

    private void formatCell(TextView cell, int score) {
        cell.setText(format(score));
        if (score == 5) {
            cell.setBackgroundColor(getColor(R.color.green));
        }
    }

    private String format(int score) {
        return String.format("%3d", score);
    }
}
