package com.reitler.got.vm;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.reitler.got.GotApplication;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.MatchDataManager;

import java.util.concurrent.ExecutorService;

public class StartViewModel extends AndroidViewModel {

    private final ExecutorService executor;
    private MatchDataManager dataManager;
    private MutableLiveData<Match> openMatch = new MutableLiveData<>();

    public StartViewModel(@NonNull Application application) {
        super(application);

        this.executor = ((GotApplication) application).executorService;
        loadGame(new MatchDataManager(((GotApplication)application).matchDataBase));
    }

    public LiveData<Match> getOpenMatch(){
        return this.openMatch;
    }

    private void loadGame(MatchDataManager dataManager) {
        executor.execute(() -> {
            Match match = dataManager.getOpenMatch();
            this.openMatch.postValue(match);
        });
    }
}
