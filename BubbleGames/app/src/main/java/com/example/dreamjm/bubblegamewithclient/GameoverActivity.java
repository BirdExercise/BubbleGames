package com.example.dreamjm.bubblegamewithclient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameoverActivity extends AppCompatActivity {
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        TextView tv = (TextView) findViewById(R.id.finalscore);
        Bundle extras = getIntent().getExtras();
        score = extras.getInt("score");
        tv.setText(Integer.toString(score));
        tv.setEnabled(false);
        final Button share_button = (Button) findViewById(R.id.share_button);
        share_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                share();
            }
        });
        final Button home_button = (Button) findViewById(R.id.home_button);
        home_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                home();
            }
        });
    }

    private void share(){
        setContentView(R.layout.login);

    }

    private void home(){
        startActivity(new Intent(GameoverActivity.this, MainActivity.class));
    }
}
