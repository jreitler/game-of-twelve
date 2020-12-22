package com.reitler.got.view;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder{

    private CheckBox user;

    public UserViewHolder(CheckBox checked,@NonNull View itemView) {
        super(itemView);
        this.user = checked;
    }

    public CheckBox getCheckBox() {
        return user;
    }

    public void setChecked(boolean checked) {
        this.user.setChecked(checked);
    }

    public void setCheckBox(CheckBox checkbox){
        this.user = checkbox;
    }


}
