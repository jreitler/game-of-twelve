package com.reitler.got.model.data.access;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;
import androidx.room.Update;

import com.reitler.got.model.data.entity.ScoreDataEntity;

import java.util.Date;
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

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT * from t_score_data as s INNER JOIN t_match as m on s.fk_match_id=m.match_id " +
            "WHERE s.fk_player_id=:playerId AND m.start_date>=:fromDate AND m.start_date<=:toDate AND m.end_date >= 0")
    List<ScoreDataEntity> getScoresForPlayer(long playerId, Date fromDate, Date toDate);

    @Query("SELECT * from t_score_data WHERE t_score_data.fk_match_id=:matchId ORDER BY player_order ASC")
    List<ScoreDataEntity> getScoresForMatch(long matchId);

    @Query("SELECT * from t_score_data WHERE rowid=:rowId ORDER BY player_order ASC")
    ScoreDataEntity getScoreForRowId(long rowId);
}
