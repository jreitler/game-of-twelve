package com.reitler.got.model.match;

import com.reitler.got.model.data.entity.PlayerEntity;
import com.reitler.got.model.data.entity.ScoreDataEntity;
import com.reitler.got.model.match.Player;
import com.reitler.got.model.match.ScoreData;
import com.reitler.got.model.match.Turn;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TurnTest {

    private Turn t;
    private Player p;
    private ScoreData data;

    @Before
    public void setUp(){
        PlayerEntity pEntity1 = new PlayerEntity();
        pEntity1.setName("player1");
        p = new Player(pEntity1);
        data = new ScoreData(new ScoreDataEntity());
        t= new Turn(p, data);
    }

    @After
    public void tearDown(){
        p = null;
        data = null;
        t = null;
    }

    @Test
    public void testIncompleteTurn(){
        assertFalse(t.isCompleted());

        for(int i = 1; i < 10; i++){
            for(int j = 0; j < 5; j++){
                t.score(i);
            }
        }

        t.finish();

        assertFalse(t.isCompleted());
        assertEquals(5, data.get(1));
        assertEquals(5, data.get(2));
        assertEquals(5, data.get(3));
        assertEquals(5, data.get(4));
        assertEquals(5, data.get(5));
        assertEquals(5, data.get(6));
        assertEquals(5, data.get(7));
        assertEquals(5, data.get(8));
        assertEquals(5, data.get(9));
        assertEquals(0, data.get(10));
        assertEquals(0, data.get(11));
        assertEquals(0, data.get(12));
    }

    @Test
    public void testCompleteTurn() {
        assertFalse(t.isCompleted());

        for (int i = 1; i <= 12; i++) {
            for (int j = 0; j < 5; j++) {
                t.score(i);
            }
        }
        t.finish();
        assertTrue(t.isCompleted());
    }

    @Test
    public void testTurnRevert(){

        assertFalse(t.isCompleted());

        for (int i = 1; i <= 12; i++) {
            for (int j = 0; j < 5; j++) {
                t.score(i);
            }
        }
        t.revertLast();
        t.finish();
        assertFalse(t.isCompleted());
        assertEquals(5, data.get(1));
        assertEquals(5, data.get(2));
        assertEquals(5, data.get(3));
        assertEquals(5, data.get(4));
        assertEquals(5, data.get(5));
        assertEquals(5, data.get(6));
        assertEquals(5, data.get(7));
        assertEquals(5, data.get(8));
        assertEquals(5, data.get(9));
        assertEquals(5, data.get(10));
        assertEquals(5, data.get(11));
        assertEquals(4, data.get(12));
    }

    @Test
    public void testInvalidScore(){
        t.score(1);
        t.score(1);
        t.score(2);
        t.finish();
        assertEquals(2, data.get(1));
        assertEquals(0, data.get(2));
    }

    @Test
    public void testInvalidScoreTooManyInvocations(){
        for(int i = 0; i < 6; i++){
            t.score(1);
        }
        t.finish();
        assertEquals(5, data.get(1));
    }
}
