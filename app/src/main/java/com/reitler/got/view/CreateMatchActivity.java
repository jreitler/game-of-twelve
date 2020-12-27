package com.reitler.got.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitler.got.databinding.ActivityCreateMatchBinding;
import com.reitler.got.model.match.Player;
import com.reitler.got.vm.CreateMatchViewModel;

import java.util.List;
import java.util.Random;

public class CreateMatchActivity extends AppCompatActivity {

    private ActivityCreateMatchBinding binding;
    private CreateMatchViewModel viewModel;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCreateMatchBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        this.viewModel = new ViewModelProvider(this).get(CreateMatchViewModel.class);

        this.binding.buttonStartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewModel.createMatch(() -> {
                    startGameActivity();
                });
            }
        });
        this.binding.buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.createPlayer("Player_" + random.nextInt());
            }
        });

        RecyclerView availablePlayersView = binding.recyclerviewAvailablePlayers;
        PlayerListAdapter availablePlayersAdapter = new PlayerListAdapter(p -> viewModel.selectPlayer(p));
        availablePlayersView.setAdapter(availablePlayersAdapter);
        availablePlayersView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getAvailablePlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                availablePlayersAdapter.setData(players);
            }
        });

        RecyclerView selectedPlayersView = binding.recyclerviewSelectedPlayers;
        PlayerListAdapter selectedUsersAdapter = new PlayerListAdapter(p -> viewModel.deselectPlayer(p));
        selectedPlayersView.setAdapter(selectedUsersAdapter);
        selectedPlayersView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getSelectedPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                selectedUsersAdapter.setData(players);
            }
        });

        viewModel.loadPlayers();
    }

    private void startGameActivity(){
        getMainExecutor().execute(() ->
                startActivity(new Intent(this, MainGameActivity.class)));
    }

}