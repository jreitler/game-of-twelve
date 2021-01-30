package com.reitler.got.model.match;

import java.util.LinkedList;
import java.util.List;

public class Turn {

    private static final int LIMIT = 5;

    private Player player;
    private ScoreData scoreData;
    private Integer activeNumber;
    private List<Action> history;

    public Turn(Player player, ScoreData scoreData) {
        this.player = player;
        this.scoreData = scoreData;
        this.history = new LinkedList<>();
    }

    public void setActiveNumber(Integer activeNumber) {
        this.activeNumber = activeNumber;
    }

    public void singleScore(int score) {
       score(score, 1);
    }

    public void complete(int score){
        score(score, 5 - scoreData.get(score));
    }

    private void score(int score, int count){
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

        Action action = new Action(score, count);
        action.apply();
        history.add(action);

        if (isCompleted(score)) {
            this.activeNumber = null;
        }
    }

    public void revertLast(){
        if(history.isEmpty()){
            return;
        }
        Action action = this.history.remove(history.size() - 1);
        action.revert();
        if(!history.isEmpty()){
            this.activeNumber = history.get(history.size()-1).score;
            if(isCompleted(this.activeNumber)){
                this.activeNumber = null;
            }
        }else {
            this.activeNumber = null;
        }
    }

    private boolean isCompleted(int pos) {
        return scoreData.get(pos) == LIMIT;
    }

    public boolean isCompleted() {
        for (int i = 1; i <= ScoreData.SIZE; i++) {
            if (!isCompleted(i)) {
                return false;
            }
        }
        return true;
    }

    public ScoreData getScoreData(){
        return this.scoreData;
    }

    public Player getPlayer() {
        return player;
    }

    public Integer getActiveNumber() {
        return this.activeNumber;
    }

    private class Action {

        int score;
        int count;

        private Action(int score, int count) {
            this.score = score;
            this.count = count;
        }

        private void apply() {
            for (int i = 0; i < count; i++) {
                scoreData.score(score);
            }
        }

        private void revert() {
            for (int i = 0; i < count; i++) {
                scoreData.decrease(score);
            }
        }
    }

}
