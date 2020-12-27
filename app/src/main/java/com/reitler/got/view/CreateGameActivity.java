package com.reitler.got.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.reitler.got.R;
import com.reitler.got.databinding.CreateGameBinding;
import com.reitler.got.vm.CreateMatchViewModel;
import com.reitler.got.vm.UserViewItem;

import java.util.List;
import java.util.Random;

public class CreateGameActivity extends AppCompatActivity {

    private CreateGameBinding binding;
    private CreateMatchViewModel viewModel;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = CreateGameBinding.inflate(getLayoutInflater());
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
                viewModel.createUser("Player_" + random.nextInt());
            }
        });

        RecyclerView userRecyclerView = findViewById(R.id.recyclerview_users);
        UserListAdapter adapter = new UserListAdapter();
        userRecyclerView.setAdapter(adapter);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        viewModel.getPlayers().observe(this, new Observer<List<UserViewItem>>() {
            @Override
            public void onChanged(List<UserViewItem> userViewItems) {
                adapter.setData(userViewItems);
                adapter.notifyDataSetChanged();
            }
        });
        viewModel.loadUsers();
    }

    private void startGameActivity(){
        getMainExecutor().execute(() ->
                startActivity(new Intent(this, MainGameActivity.class)));
    }

}