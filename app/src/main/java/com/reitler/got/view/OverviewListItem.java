package com.reitler.got.view;

import com.reitler.got.model.match.Player;

public class OverviewListItem {
    private Player player;
    private int score;

    public OverviewListItem(Player player, int score){
        this.player = player;
        this.score = score;
    }

    public String getPlayerName() {
        return player.getName();
    }

    public Player getPlayer(){
        return this.player;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

}
