package com.reitler.got.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.reitler.got.GotApplication;
import com.reitler.got.model.match.MatchDataManager;
import com.reitler.got.model.match.Player;
import com.reitler.got.model.match.PlayerManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CreateMatchViewModel extends AndroidViewModel {

    private final MatchDataManager matchDataManager;
    private ExecutorService executor;
    private PlayerManager playerManager;
    private List<Player> availablePlayers = new ArrayList<>();
    private List<Player> selectedPlayers = new ArrayList<>();

    private LiveDataContainer container = new LiveDataContainer();

    public CreateMatchViewModel(@NonNull Application application) {
        super(application);

        this.executor = ((GotApplication) application).executorService;
        this.playerManager =
                new PlayerManager(((GotApplication) application).matchDataBase.getPlayerDao());
        this.matchDataManager =
                new MatchDataManager(((GotApplication) application).matchDataBase);
    }

    public LiveData<List<Player>> getAvailablePlayers() {
        return this.container.availablePlayers;
    }

    public LiveData<List<Player>> getSelectedPlayers() {
        return this.container.selectedPlayers;
    }

    public void loadPlayers() {
        executor.execute(() -> {
            availablePlayers.addAll(playerManager.getAllPlayer());
            updatePlayers(true);
        });
    }

    public void createPlayer(String name) {
        executor.execute(() -> {
            Player created = playerManager.createPlayer(name);
            this.availablePlayers.add(created);
            updatePlayers(true);
        });
    }

    public void selectPlayer(Player p){
        boolean removed = availablePlayers.remove(p);
        if(removed){
            selectedPlayers.add(p);
            updatePlayers(false);
        }
    }

    public void deselectPlayer(Player p){
        boolean removed = selectedPlayers.remove(p);
        if(removed){
            availablePlayers.add(p);
            updatePlayers(true);
        }
    }

    public void createMatch(Runnable callback) {
        executor.execute(() -> {
            this.matchDataManager.deleteOpenMatches();
            this.matchDataManager.createMatch(this.selectedPlayers);
            callback.run();
        });
    }

    private void updatePlayers(boolean sort){
        if(sort){
            this.availablePlayers.sort(Comparator.comparing(player -> player.getName()));
        }
        container.availablePlayers.postValue(Collections.unmodifiableList(this.availablePlayers));
        container.selectedPlayers.postValue(Collections.unmodifiableList(this.selectedPlayers));
    }

    private class LiveDataContainer{
        private MutableLiveData<List<Player>> availablePlayers = new MutableLiveData<>();
        private MutableLiveData<List<Player>> selectedPlayers = new MutableLiveData<>();
    }
}
