package com.reitler.got.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.reitler.got.R;
import com.reitler.got.databinding.FragmentGameBinding;
import com.reitler.got.model.match.Turn;
import com.reitler.got.vm.MatchViewModel;

import java.util.ArrayList;
import java.util.List;


public class GameFragment extends Fragment {

    private FragmentGameBinding binding;
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
        this.binding = FragmentGameBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.buttons.add(binding.button1);
        this.buttons.add(binding.button2);
        this.buttons.add(binding.button3);
        this.buttons.add(binding.button4);
        this.buttons.add(binding.button5);
        this.buttons.add(binding.button6);
        this.buttons.add(binding.button7);
        this.buttons.add(binding.button8);
        this.buttons.add(binding.button9);
        this.buttons.add(binding.button10);
        this.buttons.add(binding.button11);
        this.buttons.add(binding.button12);

        for (int i = 1; i <= 12; i++) {
            Button button = this.buttons.get(i - 1);
            button.setOnClickListener(createOnClickListener(i));
            button.setOnLongClickListener(createOnLongClickListener(i));
            viewModel.getScore(i).observe(getViewLifecycleOwner(),
                    createButtonObserver(button, i));
        }

        binding.buttonUndo.setOnClickListener(v -> getViewModel().revert());

        viewModel.getTurn().observe(getViewLifecycleOwner(), new Observer<Turn>() {
            @Override
            public void onChanged(Turn turn) {
                if(turn == null){
                    return;
                }
                binding.gamePlayerName.setText(turn.getPlayer().getName());
            }
        });


        binding.buttonNextPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.nextPlayer();
                if (binding.buttonNextPlayer.getText().equals(getString(R.string.end_game))) {
                    requireActivity().finish();
                }
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

        viewModel.isFinalTurn().observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean finalTurn) {
                if(finalTurn){
                    binding.buttonNextPlayer.setText(R.string.end_game);
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private MatchViewModel getViewModel() {
        return viewModel;
    }

    private View.OnClickListener createOnClickListener(int index) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.addScore(index);
            }
        };
    }

    private View.OnLongClickListener createOnLongClickListener(int index) {
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