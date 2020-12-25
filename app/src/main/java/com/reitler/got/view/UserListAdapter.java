package com.reitler.got.view;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitler.got.R;
import com.reitler.got.vm.UserViewItem;

import java.util.ArrayList;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserViewHolder>{

    private View view;
    private List<UserViewItem> userList = new ArrayList<>();

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_user_list_item, parent, false);
        CheckBox checkBox = view.findViewById(R.id.user_checkBox);
        return new UserViewHolder(checkBox,view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.getCheckBox().setChecked(this.userList.get(position).isChecked());
        holder.getCheckBox().setText(this.userList.get(position).getName());
        holder.getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getUser(position).setChecked(isChecked);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void setData(List<UserViewItem> userList){
        this.userList = userList;
    }

    private UserViewItem getUser(int position){
        return userList.get(position);
    }
}
