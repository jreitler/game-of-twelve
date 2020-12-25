package com.reitler.got.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.reitler.got.R;
import com.reitler.got.model.match.Match;
import com.reitler.got.model.match.Turn;
import com.reitler.got.vm.MatchViewModel;


public class GameFragment extends Fragment {
    View.OnClickListener buttonClickListener;
    View.OnLongClickListener longClickListener;
    private MatchViewModel viewModel;

    public GameFragment() {
        buttonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View button) {
                if (getViewModel().getTurn().getValue() == null) {
                    return;
                }
                switch (button.getId()) {
                    case R.id.button_1:
                        getViewModel().addScore(1);
                        break;
                    case R.id.button_2:
                        getViewModel().addScore(2);
                        break;
                    case R.id.button_3:
                        getViewModel().addScore(3);
                        break;
                    case R.id.button_4:
                        getViewModel().addScore(4);
                        break;
                    case R.id.button_5:
                        getViewModel().addScore(5);
                        break;
                    case R.id.button_6:
                        getViewModel().addScore(6);
                        break;
                    case R.id.button_7:
                        getViewModel().addScore(7);
                        break;
                    case R.id.button_8:
                        getViewModel().addScore(8);
                        break;
                    case R.id.button_9:
                        getViewModel().addScore(9);
                        break;
                    case R.id.button_10:
                        getViewModel().addScore(10);
                        break;
                    case R.id.button_11:
                        getViewModel().addScore(11);
                        break;
                    case R.id.button_12:
                        getViewModel().addScore(12);
                        break;
                    default:
                        ;
                }
                ;
            }
        };

        longClickListener = new View.OnLongClickListener() {
            //boolean to indicate whether you have consumed the event
            @Override
            public boolean onLongClick(View button) {
                if (getViewModel().getTurn().getValue() == null) {
                    //Event can't be consumend
                    return false;
                }
                //TODO: "Fill up" scores
                switch (button.getId()) {
                    case R.id.button_1:
                        getViewModel().completeScore(1);
                        break;
                    case R.id.button_2:
                        getViewModel().completeScore(2);
                        break;
                    case R.id.button_3:
                        getViewModel().completeScore(3);
                        break;
                    case R.id.button_4:
                        getViewModel().completeScore(4);
                        break;
                    case R.id.button_5:
                        getViewModel().completeScore(5);
                        break;
                    case R.id.button_6:
                        getViewModel().completeScore(6);
                        break;
                    case R.id.button_7:
                        getViewModel().completeScore(7);
                        break;
                    case R.id.button_8:
                        getViewModel().completeScore(8);
                        break;
                    case R.id.button_9:
                        getViewModel().completeScore(9);
                        break;
                    case R.id.button_10:
                        getViewModel().completeScore(10);
                        break;
                    case R.id.button_11:
                        getViewModel().completeScore(11);
                        break;
                    case R.id.button_12:
                        getViewModel().completeScore(12);
                        break;
                    default:
                        ;
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
        this.viewModel = new ViewModelProvider(this).get(MatchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

        view.findViewById(R.id.button_undo).setOnClickListener(v -> getViewModel().revert());

        viewModel.getTurn().observe(getViewLifecycleOwner(), new Observer<Turn>() {
            @Override
            public void onChanged(Turn turn) {
                ((TextView) view.findViewById(R.id.game_playerName)).
                        setText(turn.getPlayer().getName());
            }
        });

        viewModel.getScore(1).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_1), getString(R.string.button_1_caption_template)));
        viewModel.getScore(2).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_2), getString(R.string.button_2_caption_template)));
        viewModel.getScore(3).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_3), getString(R.string.button_3_caption_template)));
        viewModel.getScore(4).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_4), getString(R.string.button_4_caption_template)));
        viewModel.getScore(5).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_5), getString(R.string.button_5_caption_template)));
        viewModel.getScore(6).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_6), getString(R.string.button_6_caption_template)));
        viewModel.getScore(7).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_7), getString(R.string.button_7_caption_template)));
        viewModel.getScore(8).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_8), getString(R.string.button_8_caption_template)));
        viewModel.getScore(9).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_9), getString(R.string.button_9_caption_template)));
        viewModel.getScore(10).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_10), getString(R.string.button_10_caption_template)));
        viewModel.getScore(11).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_11), getString(R.string.button_11_caption_template)));
        viewModel.getScore(12).observe(getViewLifecycleOwner(),
                createButtonObserver((Button) view.findViewById(R.id.button_12), getString(R.string.button_12_caption_template)));

        view.findViewById(R.id.button_nextPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.nextPlayer();
            }
        });

    }

    private MatchViewModel getViewModel() {
        return viewModel;
    }

    private Observer<Integer> createButtonObserver(Button button, String headerTemplate) {
        return new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int value = integer != null ? integer.intValue() : 0;
                button.setText(String.format(headerTemplate, value));
            }
        };
    }
}