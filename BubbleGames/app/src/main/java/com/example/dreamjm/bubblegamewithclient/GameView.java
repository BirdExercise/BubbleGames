package com.example.dreamjm.bubblegamewithclient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Dreamjm on 12/24/17.
 */

public class GameView extends View {
    private Paint paint;
    private BubbleManager BubbleLauncher;
    private final Matrix m = new Matrix();
    public float xtouch = 0;
    public float ytouch = 0;
    public int score=0;
    private static final int GAME_ROUND_TIME_MILLISECONDS = 60 * 1000;
    private final DateFormat formatter = new SimpleDateFormat("ss:SSS", Locale.UK);
    private Timer timer;
    private String mymessage = "";
    int mycount = 0;
    private Bundle extras;
    private ArrayList<Integer> backgroundList = new ArrayList<>();


    public GameView(Context context, Timer timer, Bundle extras){
        super(context);
        paint = new Paint();
        this.BubbleLauncher = new BubbleManager(getResources(), extras);
        // setBackground(getResources().getDrawable(R.drawable.background2));
        this.timer=timer;
        this.extras = extras;
        timer.startGame();
        backgroundList.add(R.drawable.background);
        backgroundList.add(R.drawable.background2);
        if (extras.getInt("selectedBackground") == 1)
            setBackgroundResource(R.drawable.background);
        else if (extras.getInt("selectedBackground") == 2)
            setBackgroundResource(R.drawable.background2);
        else {

            String path = extras.getString("selectedBackground");
            Uri imageUri = Uri.parse(path);
            try {
                InputStream imageStream = context.getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                setBackgroundDrawable(new BitmapDrawable(selectedImage));
            } catch (Exception e) {

            }

        }

    }

    @Override
    protected void onDraw(Canvas canvas){
        BubbleLauncher.maxWidth = this.getWidth();
        BubbleLauncher.maxHeight = this.getHeight();
        super.onDraw(canvas);
        if (timer.isGameFinished()) {
            Context context = getContext();
            Intent i = new Intent(context, GameoverActivity.class);
            i.putExtra("score",score);
            context.startActivity(i);
        } else {
            if (extras.getInt("selectedMode") == 1)
                BubbleLauncher.update1(this);
            else if (extras.getInt("selectedMode") == 2)
                BubbleLauncher.update2(this);

            BubbleLauncher.draw(canvas);
            this.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            xtouch = event.getX();
                            ytouch = event.getY();
                            break;
                        case MotionEvent.ACTION_POINTER_DOWN:
                            xtouch = event.getX();
                            ytouch = event.getY();
                            break;
                        default:
                            //Log.d("hehe0", Float.toString(xtouch));
                    }
                    return true;
                }
            });

            timer.draw(canvas);
            try {
                Thread.sleep(30);
            } catch (Exception e) {

            }
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawText(Integer.toString(mycount),500,1000, paint);
            invalidate();

            /*
            mycount ++;
            if (mycount > 10) {
                Random r = new Random();
                setBackgroundResource(backgroundList.get(r.nextInt(backgroundList.size())));
                mycount = 0;
            }*/


        }

    }

}
