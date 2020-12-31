package com.reitler.got.model.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchStatistic {

    private List<Integer> scores = new ArrayList<>();

    public void add(int score){
        this.scores.add(score);
    }

    public int getScoreSum(){
        return scores.stream().collect(Collectors.summingInt(Integer::intValue));
    }

    public int getPlayerCount(){
        return scores.size();
    }

    public double getAvgPlayerScore(){
        return ((double) getScoreSum()) / getPlayerCount();
    }
}