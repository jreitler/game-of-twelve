package com.reitler.got.vm;

import com.reitler.got.model.match.Player;

public class UserViewItem {
    private boolean checked;
    private Player player;

    public UserViewItem(Player player){
        this.player = player;
        this.checked = false;
    }

    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }

    public String getName(){
        return player.getName();
    }

    public Player getPlayer() {
        return player;
    }
}
