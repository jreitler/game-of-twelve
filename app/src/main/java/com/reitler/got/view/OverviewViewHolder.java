package com.reitler.got.view;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class OverviewViewHolder extends RecyclerView.ViewHolder{
    private final TextView playerName;
    private final TextView score;

    public OverviewViewHolder(TextView playerName, TextView score, View view){
        super(view);
        this.playerName = playerName;
        this.score = score;
    }

    public TextView getPlayerName() {
        return playerName;
    }

    public TextView getScore() {
        return score;
    }
}
