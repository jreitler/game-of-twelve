package com.reitler.got.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitler.got.R;
import com.reitler.got.model.match.Player;
import com.reitler.got.view.PlayerListAdapter.PlayerViewHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerListAdapter extends RecyclerView.Adapter<PlayerViewHolder> {

    private View view;
    private List<Player> playerList = new ArrayList<>();
    private PlayerSelectedListener listener;

    public PlayerListAdapter(PlayerSelectedListener listener){
        this.listener = listener;
    }

    public PlayerListAdapter(){
        this(p -> {});
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_player_list_entry, parent, false);
        TextView textView = view.findViewById(R.id.textview_player_name);
        return new PlayerViewHolder(textView, view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        holder.textView.setText(this.playerList.get(position).getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.playerSelected(playerList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return playerList.size();
    }

    public void addPlayer(Player p) {
        this.playerList.add(p);
        Collections.sort(playerList, (p1, p2) -> p1.getName().compareTo(p2.getName()));
        notifyDataSetChanged();
    }

    public void setData(List<Player> list){
        this.playerList.clear();
        this.playerList.addAll(list);
        notifyDataSetChanged();
    }

    public class PlayerViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        private PlayerViewHolder(TextView textView, View itemView) {
            super(itemView);
            this.textView = textView;
        }
    }

    public interface PlayerSelectedListener{
        public void playerSelected(Player p);
    }
}
