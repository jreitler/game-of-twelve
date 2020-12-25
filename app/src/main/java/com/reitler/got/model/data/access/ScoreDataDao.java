package com.reitler.got.model.data.access;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.reitler.got.model.data.entity.ScoreDataEntity;

import java.util.List;

@Dao
public interface ScoreDataDao {

    @Insert
    long insert(ScoreDataEntity entity);

    @Update
    void save(ScoreDataEntity entity);

    @Query("SELECT * from t_score_data")
    List<ScoreDataEntity> getAllScores();

    @Query("SELECT * from t_score_data WHERE t_score_data.fk_player_id=:playerId")
    List<ScoreDataEntity> getScoresForPlayer(long playerId);

    @Query("SELECT * from t_score_data WHERE t_score_data.fk_match_id=:matchId")
    List<ScoreDataEntity> getScoresForMatch(long matchId);

    @Query("SELECT * from t_score_data WHERE rowid=:rowId ORDER BY player_order ASC")
    ScoreDataEntity getScoreForRowId(long rowId);
}
