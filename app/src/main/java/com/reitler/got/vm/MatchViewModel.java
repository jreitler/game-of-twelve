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
import com.reitler.got.model.match.ScoreData;
import com.reitler.got.model.match.Turn;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class MatchViewModel extends AndroidViewModel {

    private ExecutorService executor;
    private Match mMatch;
    private Turn mTurn;
    private MatchDataManager dataManager;

    private LiveDataContainer container = new LiveDataContainer();

    public MatchViewModel(@NonNull Application application) {
        super(application);
        MatchDatabase matchDataBase = ((GotApplication) application).matchDataBase;
        this.dataManager = new MatchDataManager(matchDataBase);
        for (int i = 0; i < 12; i++) {
            container.scores.add(new MutableLiveData<>(0));
        }
        this.executor = ((GotApplication) application).executorService;
        loadMatch();
    }

    private void loadMatch() {
        executor.execute(() -> {
            this.mMatch = dataManager.getOpenMatch();
            matchCreated();
        });
    }

    private void matchCreated() {
        container.remainingScores.clear();
        for(Map.Entry<Player, ScoreData> entry : mMatch.getScoreDatas().entrySet()){
            container.remainingScores.put(entry.getKey(), new MutableLiveData<>(entry.getValue().remainingScore()));
        }
        container.match.postValue(mMatch);
        mTurn = mMatch.start();
        container.turn.postValue(mTurn);
        updateScores();
    }

    public void rematch(){
        executor.execute(() -> {
            List<Player> players = new ArrayList<>(mMatch.getScoreDatas().keySet());
            Player firstPlayer = players.remove(0);
            players.add(firstPlayer);

            mMatch = dataManager.createMatch(players);
            matchCreated();
        });
    }

    public void nextPlayer() {
        executor.execute(() -> {
            mTurn = container.match.getValue().nextPlayer();
            container.turn.postValue(mTurn);
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
            container.scores.get(i - 1).postValue(mTurn.getScoreData().get(i));
        }
        container.activeNumber.postValue(mTurn.getActiveNumber());

        for(Map.Entry<Player, ScoreData> entry : mMatch.getScoreDatas().entrySet()){
            container.remainingScores.get(entry.getKey()).postValue(entry.getValue().remainingScore());
        }
        if(mMatch.isFinalRound() && mMatch.isLastPlayer()){
            container.finalTurn.postValue(Boolean.TRUE);
        } else {
            container.finalTurn.postValue(Boolean.FALSE);
        }
    }


    public LiveData<Turn> getTurn() {
        return container.turn;
    }

    public LiveData<Match> getMatch() {
        return container.match;
    }

    public LiveData<Integer> getScore(int number) {
        return container.scores.get(number - 1);
    }

    public Map<Player, ? extends LiveData<Integer>> getRemainingScores(){
        return container.remainingScores;
    }

    public LiveData<Integer> getActiveNumber() {
        return container.activeNumber;
    }

    public LiveData<Boolean> isFinalTurn(){
        return container.finalTurn;
    }

    private class LiveDataContainer{
        private MutableLiveData<Match> match = new MutableLiveData<>();
        private MutableLiveData<Turn> turn = new MutableLiveData<>();
        private List<MutableLiveData<Integer>> scores = new ArrayList<>();
        private MutableLiveData<Integer> activeNumber = new MutableLiveData<>();
        private Map<Player, MutableLiveData<Integer>> remainingScores = new LinkedHashMap<>();
        private MutableLiveData<Boolean> finalTurn = new MutableLiveData<>(Boolean.FALSE);
    }
}
