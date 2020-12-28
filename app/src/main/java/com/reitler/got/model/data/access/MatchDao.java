package com.reitler.got.model.data.access;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.PlayerEntity;

import java.util.List;

@Dao
public interface MatchDao {

    @Insert
    long insert(MatchEntity match);

    @Update
    void save(MatchEntity match);

    @Query("SELECT * from t_match")
    List<MatchEntity> getAllMatches();

    @Query("SELECT * from t_match WHERE match_id=:matchId")
    MatchEntity getMatchForId(long matchId);

    @Query("SELECT * from t_match WHERE rowid=:rowId")
    MatchEntity getMatchForRowId(long rowId);

    @Query("SELECT * from t_match WHERE end_date=-1")
    MatchEntity getOpenMatch();

    @Query("DELETE from t_match WHERE end_date=-1")
    void deleteOpenMatches();
}
