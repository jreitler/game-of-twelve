package com.reitler.got.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitler.got.R;
import com.reitler.got.databinding.ActivityCreateMatchBinding;
import com.reitler.got.databinding.DialogPlayerNameBinding;
import com.reitler.got.model.match.Player;
import com.reitler.got.vm.CreateMatchViewModel;

import java.util.List;
import java.util.Set;

public class CreateMatchActivity extends BaseActivity {

    private ActivityCreateMatchBinding binding;
    private CreateMatchViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityCreateMatchBinding.inflate(getLayoutInflater());
        setContentView(this.binding.getRoot());
        this.viewModel = new ViewModelProvider(this).get(CreateMatchViewModel.class);

        this.binding.buttonStartGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewModel.createMatch(() -> {
                    startGameActivity();
                });
            }
        });
        this.binding.buttonCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });

        RecyclerView availablePlayersView = binding.recyclerviewAvailablePlayers;
        PlayerListAdapter availablePlayersAdapter = new PlayerListAdapter(p -> viewModel.selectPlayer(p));
        availablePlayersView.setAdapter(availablePlayersAdapter);
        availablePlayersView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getAvailablePlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                availablePlayersAdapter.setData(players);
            }
        });

        RecyclerView selectedPlayersView = binding.recyclerviewSelectedPlayers;
        PlayerListAdapter selectedUsersAdapter = new PlayerListAdapter(p -> viewModel.deselectPlayer(p));
        selectedPlayersView.setAdapter(selectedUsersAdapter);
        selectedPlayersView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getSelectedPlayers().observe(this, new Observer<List<Player>>() {
            @Override
            public void onChanged(List<Player> players) {
                selectedUsersAdapter.setData(players);
                if(players.isEmpty()){
                    binding.buttonStartGame.setEnabled(false);
                }
                else {
                    binding.buttonStartGame.setEnabled(true);
                }
            }
        });
    }

    private void startGameActivity() {
        getMainExecutor().execute(() ->
                startActivity(new Intent(this, MatchActivity.class)));
    }

    private void createUser() {
        Set<String> existingNames = viewModel.getExistingNames();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        DialogPlayerNameBinding dialogBinding = DialogPlayerNameBinding.inflate(getLayoutInflater());
        builder.setView(dialogBinding.getRoot());
        builder.setTitle(R.string.player_dialog_title);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                viewModel.createPlayer(dialogBinding.dialogPlayerInput.getText().toString());
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        // disable dialog to prevent users with empty names
        dialogBinding.dialogPlayerInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (existingNames.contains(s.toString())) {
                    dialogBinding.dialogPlayerInput.setError(getString(R.string.message_player_name_duplicate));
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
                } else if(s.toString().trim().isEmpty()){
                    dialogBinding.dialogPlayerInput.setHint(getString(R.string.hint_player_name));
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
                }
                else {
                    alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(true);
                }
            }
        });

        alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                dialogBinding.dialogPlayerInput.setHint(getString(R.string.hint_player_name));
                alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(false);
            }
        });

        alertDialog.show();
    }

}