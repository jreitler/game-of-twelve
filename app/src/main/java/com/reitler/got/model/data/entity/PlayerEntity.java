package com.reitler.got.model.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "t_player",
    indices =  {@Index(value = "player_name", unique = true)}
)
public class PlayerEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "player_id")
    private long playerId;

    @ColumnInfo(name = "player_name")
    private String name;

    public void setPlayerId(long playerId) {
        this.playerId = playerId;
    }

    public long getPlayerId() {
        return playerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
