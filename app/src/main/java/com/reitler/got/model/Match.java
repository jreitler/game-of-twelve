package com.reitler.got.model;

import com.reitler.got.model.data.MatchData;
import com.reitler.got.model.data.Player;

import java.util.List;

public class Match {

    private MatchData matchData;
    private List<Player> playerList;
    private int activePlayer;
    private boolean finished;
    private boolean finalRound;
    private Turn turn;

    public Match() {
        this.matchData = new MatchData();
        this.finished = false;
        this.finalRound = false;
    }

    public void addPlayer(Player player) {
        this.matchData.addPlayer(player);
    }

    public Turn start() {
        this.playerList = matchData.getPlayersAsList();
        this.activePlayer = -1;
        return nextPlayer();
    }

    public Turn nextPlayer() {
        if (turn != null) {
            if (this.turn.isCompleted())
                this.finalRound = true;

            turn.finish();
        }
        if (isLastPlayer()) {
            if (this.finalRound) {
                this.finished = true;
                return null;
            }
            activePlayer = 0;
        } else {
            activePlayer++;
        }

        Player p = playerList.get(activePlayer);
        this.turn = new Turn(p, matchData.getScores(p));
        return this.turn;
    }

    public void finish() {
        // save matchData?
    }

    public boolean isFinished(){
        return this.finished;
    }

    private boolean isLastPlayer() {
        return activePlayer == playerList.size() - 1;
    }


}
