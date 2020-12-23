package com.reitler.got.model.match;

import com.reitler.got.model.data.access.MatchDatabase;
import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.PlayerEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;

public class MatchDataManager {

    private MatchDatabase database;

    public MatchDataManager(MatchDatabase database){
        this.database = database;
    }

    public MatchEntity createMatchEntity(){
        MatchEntity matchEntity = new MatchEntity();
        long rowId = database.getMatchDao().insert(matchEntity);
        return database.getMatchDao().getMatchForRowId(rowId);
    }

    public ScoreDataEntity createScoreDataEntity(long matchId, long playerId){
        ScoreDataEntity scoreDataEntity = new ScoreDataEntity();
        scoreDataEntity.setPlayerId(playerId);
        scoreDataEntity.setMatchId(matchId);
        long rowId = database.getScoreDao().insert(scoreDataEntity);
        return database.getScoreDao().getScoreForRowId(rowId);
    }

    public PlayerEntity createPlayerEntity(String name){
        PlayerEntity entity = new PlayerEntity();
        entity.setName(name);
        long rowId = this.database.getPlayerDao().insert(entity);
        return database.getPlayerDao().getPlayerForRowId(rowId);
    }

    public void save(PlayerEntity entity){
        this.database.getPlayerDao().save(entity);
    }

    public void save(ScoreDataEntity entity){
        this.database.getScoreDao().save(entity);
    }

    public void save(MatchEntity entity){
        this.database.getMatchDao().save(entity);
    }

}
