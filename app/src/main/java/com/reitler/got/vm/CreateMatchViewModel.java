package com.reitler.got.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Transaction;

import com.reitler.got.GotApplication;
import com.reitler.got.model.match.MatchDataManager;
import com.reitler.got.model.match.Player;
import com.reitler.got.model.match.PlayerManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class CreateMatchViewModel extends AndroidViewModel {


    private final MatchDataManager matchDataManager;
    private ExecutorService executor;
    private PlayerManager playerManager;
    private MutableLiveData<List<UserViewItem>> players = new MutableLiveData<>();

    public CreateMatchViewModel(@NonNull Application application) {
        super(application);

        this.executor = ((GotApplication) application).executorService;
        this.playerManager =
                new PlayerManager(((GotApplication) application).matchDataBase.getPlayerDao());
        this.matchDataManager =
                new MatchDataManager(((GotApplication) application).matchDataBase);
    }

    public LiveData<List<UserViewItem>> getPlayers() {
        return this.players;
    }

    public void loadUsers() {
        executor.execute(() -> {
            List<Player> allPlayer = playerManager.getAllPlayer();
            this.players.postValue(allPlayer.stream().map(p -> new UserViewItem(p)).collect(Collectors.toList()));
        });
    }

    public void createUser(String name) {
        executor.execute(() -> {
            playerManager.createPlayer(name);
            loadUsers();
        });
    }

    public void createMatch(Runnable callback) {
        executor.execute(() -> {
            List<Player> selectedPlayers = new ArrayList<>();
            for (UserViewItem item : players.getValue()) {
                if (item.isChecked()) {
                    selectedPlayers.add(item.getPlayer());
                }
            }
            this.matchDataManager.deleteOpenMatches();
            this.matchDataManager.createMatch(selectedPlayers);
            callback.run();
        });
    }
}
