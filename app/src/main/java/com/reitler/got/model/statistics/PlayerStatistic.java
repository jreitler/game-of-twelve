package com.reitler.got.model.statistics;

import com.reitler.got.model.data.entity.PlayerEntity;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class PlayerStatistic {

    private PlayerEntity player;
    private List<Integer> scores = new LinkedList<>();

    public PlayerStatistic(PlayerEntity entity) {
        this.player = entity;
    }

    public void addScore(int remainingScore){
        this.scores.add(remainingScore);
    }

    public int getMatchCount(){
        return this.scores.size();
    }

    public int getWinCount(){
        return (int) this.scores.stream().filter(i -> i == 0).count();
    }

    public int highestRemaining(){
        return this.scores.stream().max(Comparator.naturalOrder()).get();
    }
}
