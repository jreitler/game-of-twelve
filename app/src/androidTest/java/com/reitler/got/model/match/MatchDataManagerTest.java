package com.reitler.got.model.match;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.reitler.got.model.data.access.MatchDatabase;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MatchDataManagerTest {

    @Test
    public void testCreateAndReloadMatch(){
        Context context = ApplicationProvider.getApplicationContext();
        MatchDatabase db = Room.inMemoryDatabaseBuilder(context, MatchDatabase.class).build();

        MatchDataManager dataManager = new MatchDataManager(db);
        PlayerManager playerManager = new PlayerManager(db.getPlayerDao());

        List<Player> playerList = new ArrayList<>();
        playerList.add(playerManager.createPlayer("Player1"));
        playerList.add(playerManager.createPlayer("Player2"));
        dataManager.createMatch(playerList);

        Match openMatch = dataManager.getOpenMatch();
        assertNotNull(openMatch);
    }
}
