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
import com.reitler.got.model.match.PlayerManager;
import com.reitler.got.model.match.Turn;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class MatchViewModel extends AndroidViewModel {

    private Turn mTurn;
    private MatchDataManager dataManager;
    private PlayerManager playerManager;
    private MutableLiveData<Match> match = new MutableLiveData<>();
    private MutableLiveData<Turn> turn = new MutableLiveData<>();
    private List<MutableLiveData<Integer>> scores = new ArrayList<>();
    private ExecutorService executor;

    public MatchViewModel(@NonNull Application application) {
        super(application);
        MatchDatabase matchDataBase = ((GotApplication) application).matchDataBase;
        this.dataManager = new MatchDataManager(matchDataBase);
        for (int i = 0; i < 12; i++) {
            scores.add(new MutableLiveData<>(0));
        }
        this.playerManager = new PlayerManager(matchDataBase.getPlayerDao());
        this.executor = ((GotApplication) application).executorService;
        loadGame();
    }

    private void loadGame() {
        executor.execute(() -> {
            Match match = dataManager.getOpenMatch();
            this.match.postValue(match);
            mTurn = match.start();
            this.turn.postValue(mTurn);
            updateScores();
        });
    }

    public LiveData<Match> getMatch() {
        return this.match;
    }

    public LiveData<Turn> getTurn() {
        return this.turn;
    }

    public LiveData<Integer> getScore(int number) {
        return this.scores.get(number - 1);
    }

    /**
     * Used for prototyping, will be removed in future
     */
    public void dummyGame() {
        executor.execute(() -> {
            List<Player> players = new ArrayList<>();
            players.add(playerManager.getOrCreatePlayerForName("Player1"));
            players.add(playerManager.getOrCreatePlayerForName("Player2"));
            players.add(playerManager.getOrCreatePlayerForName("Player3"));
            newGame(players);
        });
    }

    public void newGame(List<Player> players) {
        executor.execute(() -> {
            Match match = new Match(dataManager);
            for (Player p : players) {
                match.addPlayer(p);
            }
            this.match.postValue(match);

            mTurn = match.start();
            this.turn.postValue(mTurn);
            updateScores();
        });
    }

    public void nextPlayer() {
        executor.execute(() -> {
            mTurn = this.match.getValue().nextPlayer();
            this.turn.postValue(mTurn);
            updateScores();
        });
    }

    public void addScore(int number) {
        executor.execute(() -> {
            this.mTurn.singleScore(number);
            updateScores();
        });
    }

    public void revert() {
        executor.execute(() -> {
            this.mTurn.revertLast();
            updateScores();
        });
    }

    public void completeScore(int number) {
        executor.execute(() -> {
            this.mTurn.complete(number);
            updateScores();
        });
    }

    private void updateScores() {
        if(mTurn == null){
            return;
        }
        for (int i = 1; i <= 12; i++) {
            this.scores.get(i - 1).postValue(mTurn.getScoreData().get(i));
        }
    }
}
