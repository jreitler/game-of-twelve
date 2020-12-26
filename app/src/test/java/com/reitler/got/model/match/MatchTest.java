package com.reitler.got.model.match;


import com.reitler.got.model.data.entity.MatchEntity;
import com.reitler.got.model.data.entity.PlayerEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;
import com.reitler.got.model.match.MatchDataManager;
import com.reitler.got.model.match.Player;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.Turn;

import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class MatchTest {

    @Test
    public void testMatch(){
        PlayerEntity pEntity1 = new PlayerEntity();
        pEntity1.setName("player1");
        Player p1 = new Player(pEntity1);

        PlayerEntity pEntity2 = new PlayerEntity();
        pEntity2.setName("player2");
        Player p2 = new Player(pEntity2);

        IEntitySaveHelper dataManager = Mockito.mock(IEntitySaveHelper.class);
        Match m = new Match(dataManager, new MatchEntity());

        m.addPlayer(p1, new ScoreDataEntity());
        m.addPlayer(p2, new ScoreDataEntity());

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
