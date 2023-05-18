package com.example.works;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

public class AdminActivity extends AppCompatActivity {

    private ListView mListView;
    private UserDAO mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        Button exit = findViewById(R.id.exitBtn);

        mListView = findViewById(R.id.userListView);
        mDbHelper = new UserDAO(this);

        List<User> userList = mDbHelper.readAllUsers();
        UserListAdapter adapter = new UserListAdapter(AdminActivity.this, userList);
        mListView.setAdapter(adapter);


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(exit);
            }
        });
    }

}