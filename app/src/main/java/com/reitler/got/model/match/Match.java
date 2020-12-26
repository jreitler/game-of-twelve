package com.reitler.got.model.match;

import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Match {

    private final IEntitySaveHelper saveHelper;
    private Map<Player, ScoreData> matchData;
    private List<Player> playerList;
    private boolean finished;
    private Turn turn;
    private MatchEntity entity;

    Match(IEntitySaveHelper saveHelper, MatchEntity entity){
        this.saveHelper = saveHelper;
        this.matchData = new LinkedHashMap<>();
        this.playerList = new LinkedList<>();
        this.finished = false;
        this.entity = entity;
    }

    /**
     * Add a player with existing scores. This method should only be used when restoring a match from database
     */
    void addPlayer(Player player, ScoreDataEntity scoreDataEntity){
        this.playerList.add(player);
        this.matchData.put(player, new ScoreData(scoreDataEntity));
        saveState();
    }

    public Turn start() {
        if(this.entity.getStartDate() == null){
            this.entity.setStartDate(new Date());
            this.saveHelper.save(entity);
            entity.setActivePlayer(0);
        }

        Player p = playerList.get(entity.getActivePlayer());
        saveState();
        this.turn = new Turn(p, matchData.get(p));
        return this.turn;
    }

    public Turn nextPlayer() {
        if (isLastPlayer()) {
            if (isFinalRound()) {
                this.finished = true;
                finish();
                return null;
            }
            entity.setActivePlayer(0);
        } else {
            entity.setActivePlayer(entity.getActivePlayer() + 1);
        }

        Player p = playerList.get(entity.getActivePlayer());
        saveState();
        this.turn = new Turn(p, matchData.get(p));
        return this.turn;
    }

    private void finish() {
        this.entity.setEndDate(new Date());

        saveState();
    }

    private void saveState() {
        for(ScoreData score : this.matchData.values()){
            saveHelper.save(score.getEntity());
        }
        saveHelper.save(entity);
    }

    public boolean isFinished() {
        return this.finished;
    }

    public boolean isLastPlayer() {
        return entity.getActivePlayer() == playerList.size() - 1;
    }

    public Map<Player, ScoreData> getScoreDatas() {
        return Collections.unmodifiableMap(this.matchData);
    }

    public boolean isFinalRound() {
        for(Map.Entry<Player, ScoreData> entry : matchData.entrySet()){
            if(entry.getValue().remainingScore() == 0){
                return true;
            }
        }
        return false;
    }
}
