package com.reitler.got.model.data.access;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.reitler.got.model.data.entity.PlayerEntity;
import com.reitler.got.model.match.Player;

import java.util.List;

@Dao
public interface PlayerDao {

    @Insert
    long insert(PlayerEntity player);

    @Update
    void save(PlayerEntity player);

    @Query("SELECT * from t_player")
    List<PlayerEntity> getAllPlayers();

    @Query("SELECT * from t_player WHERE t_player.player_id=:id")
    PlayerEntity getPlayerForId(long id);

    @Query("SELECT * from t_player WHERE t_player.player_name=:name")
    PlayerEntity getPlayerForName(String name);

    @Query("SELECT * from t_player WHERE rowid=:rowId")
    PlayerEntity getPlayerForRowId(long rowId);
}
