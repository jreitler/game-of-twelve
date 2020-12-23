package com.reitler.got.model.match;

import com.reitler.got.model.data.entity.PlayerEntity;

public class Player {

    private PlayerEntity entity;

    public Player(PlayerEntity entity){
        this.entity = entity;
    }

    public long getId(){
        return this.entity.getPlayerId();
    }

    public String getName(){
        return this.entity.getName();
    }
}

