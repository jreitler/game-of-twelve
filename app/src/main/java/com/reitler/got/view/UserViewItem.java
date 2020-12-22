package com.reitler.got.view;

public class UserViewItem {
    private boolean checked;
    private String name;

    public UserViewItem(String name){
        this.name = name;
        this.checked = false;
    }

    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }

    public String getName(){
        return name;
    }
}
