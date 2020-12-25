package com.reitler.got;

import android.app.Application;

import androidx.room.Room;

import com.reitler.got.model.data.access.MatchDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GotApplication extends Application {

    public MatchDatabase matchDataBase;

    public ExecutorService executorService;

    public void onCreate() {
        super.onCreate();
        matchDataBase = Room.databaseBuilder(getApplicationContext(),
                MatchDatabase.class, "MatchDataBase").build();

        executorService = Executors.newFixedThreadPool(4);

    }
}
