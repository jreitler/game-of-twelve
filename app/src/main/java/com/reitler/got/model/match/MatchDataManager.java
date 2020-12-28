package com.reitler.got.model.match;

import com.reitler.got.model.data.access.MatchDatabase;
import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.PlayerEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;

import java.util.List;

public class MatchDataManager implements IEntitySaveHelper{

    private MatchDatabase database;

    public MatchDataManager(MatchDatabase database) {
        this.database = database;
    }

    public MatchEntity createMatchEntity() {
        MatchEntity matchEntity = new MatchEntity();
        long rowId = database.getMatchDao().insert(matchEntity);
        return database.getMatchDao().getMatchForRowId(rowId);
    }

    public ScoreDataEntity createScoreDataEntity(long matchId, long playerId, int order) {
        ScoreDataEntity scoreDataEntity = new ScoreDataEntity();
        scoreDataEntity.setPlayerId(playerId);
        scoreDataEntity.setMatchId(matchId);
        scoreDataEntity.setOrder(order);
        long rowId = database.getScoreDao().insert(scoreDataEntity);
        return database.getScoreDao().getScoreForRowId(rowId);
    }

    public Match getOpenMatch() {
        MatchEntity openMatch = database.getMatchDao().getOpenMatch();
        if (openMatch == null) {
            return null;
        }
       return restoreMatch(openMatch);
    }

    public void deleteOpenMatches() {
        database.getMatchDao().deleteOpenMatches();
    }

    public void save(ScoreDataEntity entity) {
        this.database.getScoreDao().save(entity);
    }

    public void save(MatchEntity entity) {
        this.database.getMatchDao().save(entity);
    }

    public Match createMatch(List<Player> selectedPlayers) {
        MatchEntity matchEntity = createMatchEntity();
        Match match = new Match(this, matchEntity);

        for (int i = 0; i < selectedPlayers.size(); i++) {
            Player p = selectedPlayers.get(i);
            ScoreDataEntity scoreDataEntity =
                    createScoreDataEntity(matchEntity.getMatchId(), p.getId(), i);
            match.addPlayer(p, scoreDataEntity);
        }

        return match;
    }

    public Match getMatch(long matchId) {
        MatchEntity matchEntity = database.getMatchDao().getMatchForId(matchId);
        return restoreMatch(matchEntity);
    }

    private Match restoreMatch(MatchEntity entity){
        Match m = new Match(this, entity);
        List<ScoreDataEntity> scoresForMatch = database.getScoreDao().getScoresForMatch(entity.getMatchId());
        for (ScoreDataEntity s : scoresForMatch) {
            PlayerEntity playerForId = database.getPlayerDao().getPlayerForId(s.getPlayerId());
            m.addPlayer(new Player(playerForId), s);
        }
        return m;
    }
}
