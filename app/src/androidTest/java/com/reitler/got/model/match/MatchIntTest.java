package com.reitler.got.model.match;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.reitler.got.model.data.access.MatchDatabase;
import com.reitler.got.model.data.entity.PlayerEntity;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(AndroidJUnit4.class)
public class MatchIntTest {

    @Test
    public void testMatch(){
        Context context = ApplicationProvider.getApplicationContext();
        MatchDatabase db = Room.inMemoryDatabaseBuilder(context, MatchDatabase.class).build();


        MatchDataManager dataManager = new MatchDataManager(db);
        PlayerManager playerManager = new PlayerManager(db.getPlayerDao());

        Player p1 = playerManager.createPlayer("player1");

        Player p2 = playerManager.createPlayer("player2");

        Match m = dataManager.createMatch(Arrays.asList(p1, p2));

        // p1
        Turn t = m.start();
        assertNotNull(t);
        score(t, 1, 4);

        // p2
        t = m.nextPlayer();
        assertNotNull(t);

        score(t, 1, 5);
        score(t, 2, 5);
        score(t, 3, 3);

        // p1
        t = m.nextPlayer();
        assertNotNull(t);
        score(t, 1,1);
        score(t, 2, 5);
        score(t, 3, 5);
        score(t, 4, 5);
        score(t, 5, 5);
        score(t, 6, 5);
        score(t, 7, 4);

        // p2
        t = m.nextPlayer();
        assertNotNull(t);
        score(t, 3, 2);
        score(t, 4, 5);
        score(t, 5, 5);
        score(t, 6, 5);
        score(t, 7, 5);
        score(t, 8, 5);
        score(t, 9, 5);
        score(t, 10, 4);

        // p1
        t = m.nextPlayer();
        assertNotNull(t);

        score(t, 7, 1);
        score(t, 8, 5);
        score(t, 9, 5);
        score(t, 10, 5);
        score(t, 11, 5);
        score(t, 12, 5);

        // p2
        t = m.nextPlayer();
        assertNotNull(t);

        score(t, 10, 5);
        score(t, 11, 2);

        t = m.nextPlayer();
        assertNull(t);

    }

    private void score(Turn t, int nr, int count){
        for(int i = 0; i < count; i ++){
            t.singleScore(nr);
        }
    }
}
