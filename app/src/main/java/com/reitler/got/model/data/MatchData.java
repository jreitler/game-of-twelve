package com.reitler.got.model.data;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MatchData {

    private Map<Player, ScoreData> players = new LinkedHashMap<>();

    public void addPlayer(Player player){
        players.put(player, new ScoreData());
    }

    public ScoreData getScores(Player player){
        return players.get(player);
    }

    public List<Player> getPlayersAsList(){
        return new ArrayList<>(players.keySet());
    }
}
