package com.reitler.got.model;

import com.reitler.got.model.data.Player;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class MatchTest {

    @Test
    public void testMatch(){
        Player p1 = new Player("player1");
        Player p2 = new Player("player2");

        Match m = new Match();

        m.addPlayer(p1);
        m.addPlayer(p2);

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
            t.score(nr);
        }
    }
}
