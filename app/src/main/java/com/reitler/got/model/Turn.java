package com.reitler.got.model;

import com.reitler.got.model.data.Player;
import com.reitler.got.model.data.ScoreData;

import java.util.LinkedList;
import java.util.List;

public class Turn {

    private static final int LIMIT = 5;

    private Player player;
    private ScoreData scoreData;
    private Integer activeNumber;
    private List<Integer> history;

    public Turn(Player player, ScoreData scoreData) {
        this.player = player;
        this.scoreData = scoreData;
        this.history = new LinkedList<>();
    }

    public void setActiveNumber(Integer activeNumber) {
        this.activeNumber = activeNumber;
    }

    public void score(int score) {
        if (isCompleted(score)) {
            // TODO: throw error?
            return;
        }
        if (this.activeNumber == null) {
            this.activeNumber = score;
        } else if (activeNumber.intValue() != score) {
            // TODO: throw error?
            return;
        }

        history.add(score);

        if (isCompleted(score)) {
            this.activeNumber = null;
        }
    }

    public void finish(){
        history.forEach(i -> scoreData.score(i));
        history.clear();
    }

    public void revertLast(){
        this.history.remove(history.size() - 1);
    }

    private boolean isCompleted(int pos) {
        long count = history.stream().filter(i -> i.intValue() == pos).count();
        return scoreData.get(pos) + count == LIMIT;
    }

    public boolean isCompleted() {
        for (int i = 1; i <= ScoreData.SIZE; i++) {
            if (!isCompleted(i)) {
                return false;
            }
        }
        return true;
    }
}
