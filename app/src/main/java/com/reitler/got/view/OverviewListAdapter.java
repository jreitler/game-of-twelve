package com.reitler.got.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.reitler.got.R;

import java.util.List;

public class OverviewListAdapter extends RecyclerView.Adapter<OverviewViewHolder> {

    private List<OverviewListItem> list;
    private View view;

    public OverviewListAdapter(List<OverviewListItem> itemList){
        list = itemList;
    }

    @NonNull
    @Override
    public OverviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_overview_list_item, parent, false);
        TextView playerName = view.findViewById(R.id.overview_item_name);
        TextView score = view.findViewById(R.id.overview_item_score);
        return new OverviewViewHolder(playerName,score, view);
    }

    @Override
    public void onBindViewHolder(@NonNull OverviewViewHolder holder, int position) {
        holder.getPlayerName().setText(list.get(position).getPlayerName());
        holder.getScore().setText(""+list.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
