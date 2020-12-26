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
import com.reitler.got.model.match.Turn;
import com.reitler.got.vm.MatchViewModel;

import java.util.ArrayList;
import java.util.List;


public class GameFragment extends Fragment {

    private MatchViewModel viewModel;

    private List<Button> buttons = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel = new ViewModelProvider(requireActivity()).get(MatchViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.buttons.add((Button) view.findViewById(R.id.button_1));
        this.buttons.add((Button) view.findViewById(R.id.button_2));
        this.buttons.add((Button) view.findViewById(R.id.button_3));
        this.buttons.add((Button) view.findViewById(R.id.button_4));
        this.buttons.add((Button) view.findViewById(R.id.button_5));
        this.buttons.add((Button) view.findViewById(R.id.button_6));
        this.buttons.add((Button) view.findViewById(R.id.button_7));
        this.buttons.add((Button) view.findViewById(R.id.button_8));
        this.buttons.add((Button) view.findViewById(R.id.button_9));
        this.buttons.add((Button) view.findViewById(R.id.button_10));
        this.buttons.add((Button) view.findViewById(R.id.button_11));
        this.buttons.add((Button) view.findViewById(R.id.button_12));

        for (int i = 1; i <= 12; i++) {
            Button button = this.buttons.get(i - 1);
            button.setOnClickListener(createOnClickListener(i));
            button.setOnLongClickListener(createOnLongClickListener(i));
            viewModel.getScore(i).observe(getViewLifecycleOwner(),
                    createButtonObserver(button, i));
        }

        view.findViewById(R.id.button_undo).setOnClickListener(v -> getViewModel().revert());

        viewModel.getTurn().observe(getViewLifecycleOwner(), new Observer<Turn>() {
            @Override
            public void onChanged(Turn turn) {
                ((TextView) view.findViewById(R.id.game_playerName)).
                        setText(turn.getPlayer().getName());
            }
        });


        view.findViewById(R.id.button_nextPlayer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.nextPlayer();
            }
        });

        viewModel.getActiveNumber().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if (integer == null) {
                    buttons.forEach(b -> b.setEnabled(true));
                } else {
                    for (int i = 1; i <= 12; i++) {
                        buttons.get(i - 1).setEnabled(i == integer.intValue());
                    }
                }
            }
        });

    }

    private MatchViewModel getViewModel() {
        return viewModel;
    }

    private View.OnClickListener createOnClickListener(int index){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addScore(index);
            }
        };
    }

    private View.OnLongClickListener createOnLongClickListener(int index){
        return new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                viewModel.completeScore(index);
                return true;
            }
        };
    }

    private Observer<Integer> createButtonObserver(Button button, int index) {
        return new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                int value = integer != null ? integer.intValue() : 0;
                button.setText(String.format("%2d (%d)", index, value));
            }
        };
    }
}