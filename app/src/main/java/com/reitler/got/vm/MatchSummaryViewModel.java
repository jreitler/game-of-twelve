package com.reitler.got.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.reitler.got.GotApplication;
import com.reitler.got.model.data.access.MatchDatabase;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.MatchDataManager;
import com.reitler.got.model.match.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MatchSummaryViewModel extends AndroidViewModel {

    private ExecutorService executor;
    private final MatchDataManager dataManager;
    private MutableLiveData<Match> match = new MutableLiveData<>();

    public MatchSummaryViewModel(@NonNull Application application) {
        super(application);
        GotApplication gotApplication = (GotApplication) application;
        this.executor = gotApplication.executorService;
        MatchDatabase matchDataBase = gotApplication.matchDataBase;
        this.dataManager = new MatchDataManager(matchDataBase);
    }

    public void init(long matchId) {
        if (matchId < 0) {
            throw new IllegalArgumentException("Illegal Match Id passed");
        }
        executor.execute(() -> {
            this.match.postValue(dataManager.getMatch(matchId));
        });
    }

    public void initOpenMatch() {
        executor.execute(() -> {
            this.match.postValue(dataManager.getOpenMatch());
        });
    }

    public LiveData<Match> getMatch() {
        return match;
    }

    public void rematch(Runnable callback) {
        executor.execute(() -> {
            List<Player> players = new ArrayList<>(match.getValue().getScoreDatas().keySet());
            Player firstPlayer = players.remove(0);
            players.add(firstPlayer);

            dataManager.createMatch(players);
            callback.run();
        });
    }
}
