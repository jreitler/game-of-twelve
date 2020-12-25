package com.reitler.got.model.match;

import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Match {

    private final MatchDataManager dataManager;
    private Map<Player, ScoreData> matchData;
    private List<Player> playerList;
    private int activePlayer;
    private boolean finished;
    private boolean finalRound;
    private Turn turn;
    private MatchEntity entity;

    public Match(MatchDataManager dataManager) {
        this.dataManager = dataManager;
        this.matchData = new HashMap<>();
        this.playerList = new LinkedList<>();
        this.finished = false;
        this.finalRound = false;
        this.entity = dataManager.createMatchEntity();  
    }

    public void addPlayer(Player player) {
        this.playerList.add(player);
        ScoreDataEntity entity = dataManager.createScoreDataEntity(this.entity.getMatchId(), player.getId());
        this.matchData.put(player, new ScoreData(entity));
    }

    public Turn start() {
        this.entity.setStartDate(new Date());
        this.dataManager.save(entity);
        this.activePlayer = -1;
        return nextPlayer();
    }

    public Turn nextPlayer() {
        if (turn != null) {
            if (this.turn.isCompleted())
                this.finalRound = true;
        }
        if (isLastPlayer()) {
            if (this.finalRound) {
                this.finished = true;
                finish();
                return null;
            }
            activePlayer = 0;
        } else {
            activePlayer++;
        }

        Player p = playerList.get(activePlayer);
        this.turn = new Turn(p, matchData.get(p));
        return this.turn;
    }

    private void finish() {
        this.entity.setEndDate(new Date());
        dataManager.save(entity);

        for(ScoreData score : this.matchData.values()){
            dataManager.save(score.getEntity());
        }
    }

    public boolean isFinished() {
        return this.finished;
    }

    private boolean isLastPlayer() {
        return activePlayer == playerList.size() - 1;
    }


}
