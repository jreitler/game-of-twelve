package com.reitler.got.model.statistics;

import com.reitler.got.model.ScoreDataUtil;
import com.reitler.got.model.data.access.MatchDatabase;
import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.PlayerEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;

import java.util.List;

public class StatisticsDataManager {

    private MatchDatabase database;

    public StatisticsDataManager(MatchDatabase db) {
        this.database = db;
    }

    public PlayerStatistic loadPlayerStatistic(PlayerEntity entity) {
        PlayerStatistic result = new PlayerStatistic(entity);

        List<ScoreDataEntity> scores = database.getScoreDao().getScoresForPlayer(entity.getPlayerId());
        scores.forEach(s -> result.addScore(ScoreDataUtil.getRemainingScore(s)));

        return result;
    }

    public MatchStatistics loadMatchStatistics() {
        MatchStatistics result = new MatchStatistics();

        List<MatchEntity> allMatches = database.getMatchDao().getAllMatches();
        allMatches.forEach(m -> result.addMatch(getMatchStatistic(m)));

        return result;
    }

    private MatchStatistic getMatchStatistic(MatchEntity entity) {
        MatchStatistic result = new MatchStatistic();

        List<ScoreDataEntity> scoresForMatch =
                database.getScoreDao().getScoresForMatch(entity.getMatchId());
        scoresForMatch.forEach(s -> result.add(ScoreDataUtil.getRemainingScore(s)));
        return result;
    }
}
