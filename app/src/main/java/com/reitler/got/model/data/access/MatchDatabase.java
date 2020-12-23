package com.reitler.got.model.data.access;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.PlayerEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;

@Database(entities = {MatchEntity.class, PlayerEntity.class, ScoreDataEntity.class}, version = 1)
@TypeConverters({DateConverter.class})
public abstract class MatchDatabase extends RoomDatabase {

    public abstract MatchDao getMatchDao();

    public abstract PlayerDao getPlayerDao();

    public abstract ScoreDataDao getScoreDao();
}
