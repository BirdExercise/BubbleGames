package com.example.dreamjm.bubblegamewithclient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Dreamjm on 12/24/17.
 */

public class GameManager extends AppCompatActivity {
    private Timer timer = new Timer();
    GameView gameview;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        Bundle extras = getIntent().getExtras();
        gameview = new GameView(this, timer, extras);
        setContentView(gameview);
    }



}
