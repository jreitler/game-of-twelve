package com.reitler.got.model.match;

import com.reitler.got.model.data.access.PlayerDao;
import com.reitler.got.model.data.entity.PlayerEntity;

import java.util.List;
import java.util.stream.Collectors;

public class PlayerManager {

    private PlayerDao access;

    public PlayerManager(PlayerDao access){
        this.access = access;
    }

    public List<Player> getAllPlayer(){
        return access.getAllPlayers().stream()
                .map(p -> new Player(p)).collect(Collectors.toList());
    }

    public Player createPlayer(String name){
        return new Player(createPlayerEntity(name));
    }

    public Player getOrCreatePlayerForName(String name){
        PlayerEntity entity = access.getPlayerForName(name);
        if(null == entity){
            entity = createPlayerEntity(name);
        }
        return new Player(entity);
    }

    private PlayerEntity createPlayerEntity(String name){
        PlayerEntity entity = new PlayerEntity();
        entity.setName(name);
        long rowId = access.insert(entity);
        return access.getPlayerForRowId(rowId);
    }
}
