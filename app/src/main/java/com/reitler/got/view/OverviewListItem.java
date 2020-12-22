package com.reitler.got.view;

public class OverviewListItem {
    private String playerName;
    private int score;

    public OverviewListItem(String playerName, int score){
        this.playerName = playerName;
        this.score = score;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

}
