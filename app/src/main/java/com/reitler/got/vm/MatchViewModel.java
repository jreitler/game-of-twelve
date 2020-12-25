package com.reitler.got.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.reitler.got.model.data.access.MatchDatabase;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.MatchDataManager;
import com.reitler.got.model.match.Player;
import com.reitler.got.model.match.Turn;

import java.util.ArrayList;
import java.util.List;

public class MatchViewModel extends AndroidViewModel {

    private MatchDataManager dataManager;
    private MutableLiveData<Match> match = new MutableLiveData<>();
    private MutableLiveData<Turn> turn = new MutableLiveData<>();
    private List<MutableLiveData<Integer>> scores = new ArrayList<>();

    public MatchViewModel(@NonNull Application application) {
        super(application);
        MatchDatabase matchDataBase = Room.databaseBuilder(application.getApplicationContext(),
                MatchDatabase.class, "MatchDataBase").build();
        this.dataManager = new MatchDataManager(matchDataBase);
        for(int i = 0; i < 12; i++){
            scores.add(new MutableLiveData<>(0));
        }
    }

    public LiveData<Match> getMatch(){
        return this.match;
    }

    public LiveData<Turn> getTurn(){
        return this.turn;
    }

    public LiveData<Integer> getScore(int number){
        return this.scores.get(number);
    }

    public void newGame(List<Player> players) {
        Match match = new Match(dataManager);
        for(Player p : players){
            match.addPlayer(p);
        }
        this.match.setValue(match);

        Turn t = match.start();
        this.turn.setValue(t);
        updateScores();
    }

    public void nextPlayer(){
        this.turn.setValue(this.match.getValue().nextPlayer());
        updateScores();
    }

    public void addScore(int number){
        this.turn.getValue().singleScore(number);
        updateScores();
    }

    public void revert(){
        this.turn.getValue().revertLast();
        updateScores();
    }

    public void completeScore(int number){
       this.turn.getValue().complete(number);
       updateScores();
    }

    private void updateScores() {
        for(int i = 1; i <=12; i++){
            this.scores.get(i-1).setValue(turn.getValue().getScoreData().get(i));
        }
    }
}
