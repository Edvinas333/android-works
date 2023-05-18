package com.example.works;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private WorkDAO mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        EditText wcomment = findViewById(R.id.etNewItem);
        Button exit = findViewById(R.id.exitBtn);
        Button add = findViewById(R.id.btnAddItem);

        mListView = findViewById(R.id.listView);
        mDbHelper = new WorkDAO(this);

        List<Work> workList = mDbHelper.readAll();
        WorkListAdapter adapter = new WorkListAdapter(MainActivity.this, workList);
        mListView.setAdapter(adapter);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = wcomment.getText().toString();
                if (!comment.equals("")) {
                    Work newWork = new Work(comment);
                    boolean success = mDbHelper.insertData(newWork);
                    if (success) {
                        Toast.makeText(MainActivity.this, "Darbas sukurtas!", Toast.LENGTH_SHORT).show();
                        wcomment.getText().clear();
                        loadWorkList();
                    } else {
                        loadWorkList();
                        wcomment.getText().clear();
                        Toast.makeText(MainActivity.this, "Nepavyko sukurti darbo!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Negalima pridėti tuščio darbo!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent exit = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(exit);
            }
        });
    }

    private void loadWorkList() {
        List<Work> workList = mDbHelper.readAll();
        WorkListAdapter adapter = new WorkListAdapter(MainActivity.this, workList);
        mListView.setAdapter(adapter);
    }
}




