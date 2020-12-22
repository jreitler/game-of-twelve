package com.reitler.got.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reitler.got.R;
import com.reitler.got.model.Match;
import com.reitler.got.model.Turn;
import com.reitler.got.model.data.Player;

public class GameFragment extends Fragment {
    Match match = new Match();
    Turn turn;
    View.OnClickListener buttonClickListener;
    View.OnLongClickListener longClickListener;

    public GameFragment() {
        match.addPlayer(new Player("Spieler 1"));
        match.addPlayer(new Player("Spieler 2"));
        turn = match.start();
        buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                if(getTurn() == null){
                    return;
                }
                switch (button.getId()){
                    case R.id.button_1: getTurn().score(1); break;
                    case R.id.button_2: getTurn().score(2); break;
                    case R.id.button_3: getTurn().score(3); break;
                    case R.id.button_4: getTurn().score(4); break;
                    case R.id.button_5: getTurn().score(5); break;
                    case R.id.button_6: getTurn().score(6); break;
                    case R.id.button_7: getTurn().score(7); break;
                    case R.id.button_8: getTurn().score(8); break;
                    case R.id.button_9: getTurn().score(9); break;
                    case R.id.button_10: getTurn().score(10); break;
                    case R.id.button_11: getTurn().score(11); break;
                    case R.id.button_12: getTurn().score(12); break;
                    default:;
                };
            }
        };

        longClickListener = new View.OnLongClickListener() {
            //boolean to indicate whether you have consumed the event
            @Override
            public boolean onLongClick(View button) {
                if(getTurn() == null){
                    //Event can't be consumend
                    return false;
                }
                //TODO: "Fill up" scores
                switch (button.getId()){
                    case R.id.button_1: break;
                    case R.id.button_2: break;
                    case R.id.button_3: break;
                    case R.id.button_4: break;
                    case R.id.button_5: break;
                    case R.id.button_6: break;
                    case R.id.button_7: break;
                    case R.id.button_8: break;
                    case R.id.button_9: break;
                    case R.id.button_10: break;
                    case R.id.button_11: break;
                    case R.id.button_12: break;
                    default:;
                }
                return true;
            }
        };

    }

    public static GameFragment newInstance(String param1, String param2) {
        GameFragment fragment = new GameFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        view.findViewById(R.id.button_1).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_2).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_3).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_4).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_5).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_6).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_7).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_8).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_9).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_10).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_11).setOnClickListener(buttonClickListener);
        view.findViewById(R.id.button_12).setOnClickListener(buttonClickListener);

        view.findViewById(R.id.button_1).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_2).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_3).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_4).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_5).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_6).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_7).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_8).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_9).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_10).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_11).setOnLongClickListener(longClickListener);
        view.findViewById(R.id.button_12).setOnLongClickListener(longClickListener);

        ((TextView) view.findViewById(R.id.game_playerName)).setText(turn.getPlayer().getName());

        view.findViewById(R.id.button_undo).setOnClickListener(v -> getTurn().revertLast());

    }

    private Turn getTurn(){
        return this.turn;
    }
}