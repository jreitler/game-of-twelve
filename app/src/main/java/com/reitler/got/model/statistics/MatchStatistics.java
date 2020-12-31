package com.reitler.got.model.statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MatchStatistics {

    private List<MatchStatistic> matches = new ArrayList<>();

    public int getCount(){
        return matches.size();
    }

    public double getAvgScoresPerMatch(){
        double absoluteScores = matches.stream().collect(Collectors.summingDouble(MatchStatistic::getScoreSum));
        return absoluteScores / getCount();
    }

    public double getAvgScoresPerPlayer(){
        double sumAvgScores = matches.stream().collect(Collectors.summingDouble(MatchStatistic::getAvgPlayerScore));
        return sumAvgScores
                / getCount();
    }

    public void addMatch(MatchStatistic match){
        this.matches.add(match);
    }
}
