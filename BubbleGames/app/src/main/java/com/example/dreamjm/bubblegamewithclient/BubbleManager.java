package com.example.dreamjm.bubblegamewithclient;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Region;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * Created by Dreamjm on 12/24/17.
 */

public class BubbleManager {
    public SparseArray<Bitmap> bitmapCache;
    public List<Bubbles> Bubbles = new ArrayList<Bubbles>();
    private final Random random = new Random();

    public int maxWidth;
    public int maxHeight;
    private int id;
    private Bundle extras;



    private Socket mysocket = null;
    private DataOutputStream os = null;
    private DataInputStream is = null;
    private static final String hostname = "localhost";
    private int portNum = 9999;
    private double n1 = 0.0, n2 = 0.0;

    public BubbleManager(Resources r, Bundle extras) {
        this.extras = extras;
        bitmapCache = new SparseArray<Bitmap>(DifferentBubble.values().length);
        for (DifferentBubble t : DifferentBubble.values()) {
            bitmapCache.put(t.getResourceId(), BitmapFactory.decodeResource(r, t.getResourceId(), new BitmapFactory.Options()));
        }
        new Thread(new ClientThread()).start();
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(20);canvas.drawText(Double.toString(n1),300,500, paint);
        canvas.drawText(Double.toString(n2),600,500, paint);

        for (Bubbles b : Bubbles) {
            b.draw(canvas);
        }
    }


    public void update1(GameView view) {
        if (maxWidth < 0 || maxHeight < 0) {
            return;
        }

        if (random.nextInt(1000) <= 30) {
            Bubbles.add(createNewBubble(view));
        }

        for (Iterator<Bubbles> iter = Bubbles.iterator(); iter.hasNext();) {

            Bubbles bubble = iter.next();
            bubble.move1();
            OnTouchListerner(view);
            if (bubble.hasMovedOffScreen()) {
                iter.remove();
            }
        }
    }

    public void update2(GameView view) {
        if (maxWidth < 0 || maxHeight < 0) {
            return;
        }

        if (Bubbles.size() < 10 && random.nextInt(1000) <= 30) {
            Bubbles.add(createNewBubble(view));
        }

        for (Iterator<Bubbles> iter = Bubbles.iterator(); iter.hasNext();) {
            Bubbles bubble = iter.next();
            bubble.move2();
            OnTouchListerner(view);
        }
    }

    public Bubbles createNewBubble(GameView view) {
        int angle = random.nextInt(20)+70;
        int speed = random.nextInt(50);
        boolean rightToLeft=random.nextBoolean();
        float gravity=random.nextInt(4)+6.0f;
        int xStart = random.nextInt(this.maxWidth);
        float rotationStartingAngle = random.nextInt(360);
        float rotationIncrement = random.nextInt(100) / 10.0f;
        if (random.nextInt(1) % 2 == 0) {
            rotationIncrement *= -1;
        }

        int indexRandom = random.nextInt(DifferentBubble.values().length);
        id = DifferentBubble.randomBubble(indexRandom).getResourceId();
        return new Bubbles(bitmapCache.get(id), maxWidth, maxHeight,
                angle, speed, gravity, xStart, rightToLeft, rotationIncrement, rotationStartingAngle, indexRandom, view, extras.getInt("selectedLevel"));

    }

    public int OnTouchListerner(GameView view){
        for (Bubbles bubble : Bubbles) {
            if(!bubble.isActive)
                continue;

            // MotionEvent event = MotionEvent.obtain(downTime, eventTime, action, 0, 0, metaState);
            // Log.d("hehe", "hehe");
            if (n1!=0) {
                //Log.d("yposition", Double.toString(view.ytouch));
                //Log.d("bubbleypos",Double.toString(bubble.yLocation));
                double x1=n1, y1=n2;
                Log.d("x1",Double.toString(x1));
                Log.d("y1",Double.toString(y1));

                float  x2=bubble.xLocation, y2=bubble.yLocation;
                if((x2-x1)*(x2-x1)+(maxHeight-y1-y2)*(maxHeight-y1-y2)<=80000 ){
                    bubble.isActive=false;
                    view.score++;
                    Log.d("score",Integer.toString(view.score));
                }
            }
        }
        return 0;
    }

    private class ClientThread implements Runnable
    {

        @Override
        public void run()
        {
            try {
                mysocket = new Socket(hostname, portNum); // the hostname can be either "localhost" or 127.0.0.1
                os = new DataOutputStream(mysocket.getOutputStream());
                is = new DataInputStream(mysocket.getInputStream());
            } catch (UnknownHostException e) {
                System.err.println("Don't know about host hostname");
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to: hostname");
            }


            if (mysocket != null && (os != null) && (is != null)) {
                while (true) {
                    try {
                        n1 = is.readDouble();
                        n2 = is.readDouble();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            if (mysocket != null && (os != null) && (is != null)) {
                try {
                    is.close();
                    os.close();
                    mysocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
