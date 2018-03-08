package com.example.dreamjm.bubblegamewithclient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

import java.util.Random;


public class Bubbles {
    private final Paint paint = new Paint();
    private final Matrix m = new Matrix();

    private final Bitmap bubble;
    private final float rotationIncrement;

    private float gravity;
    private final int maxWidth;
    private final int maxHeight;
    private final int angle;
    private int initialSpeed;
    private final int xStart;
    private int count=6;

    public int xLocation = -1;
    public int yLocation = -1;
    private final Random random = new Random();
    private int xVelocity = random.nextInt(50);
    private int yVelocity = random.nextInt(50);
    private int absYLocation;
    private float rotationAngle;
    private float time = 0.1f;
    private float fallingVelocity = 1.0f;
    private boolean rightToLeft;
    public boolean isActive = true;
    private int id;
    private GameView view;
    private int level;

    public Bubbles(Bitmap bubble, int maxWidth, int maxHeight, int angle, int initialSpeed, float gravity, int xStart,
                  boolean rightToLeft, float rotationIncrement, float rotationStartingAngle, int id, GameView view, int level) {

        this.bubble = bubble;
        this.maxHeight = maxHeight;
        this.angle = angle;
        this.initialSpeed = initialSpeed*level;
        this.gravity = gravity*level;
        this.maxWidth = maxWidth;
        this.rightToLeft = rightToLeft;
        this.rotationIncrement = rotationIncrement;
        this.rotationAngle = rotationStartingAngle;
        this.xStart = xStart;
        paint.setAntiAlias(true);
        this.id = id;
        this.view = view;
        this.level = level;
        this.xVelocity = xVelocity*level;
        this.yVelocity = yVelocity*level;

    }

    public void move1() {
        if (isActive) {
            xLocation = (int) (xStart+ initialSpeed * time);
            yLocation = (int) (maxHeight-0.5 * gravity * time * time );

            if (rightToLeft) {
                xLocation = maxWidth - bubble.getWidth() - xLocation;
            }
        } else {

            yLocation -= time * (fallingVelocity + time * gravity / 2);
            //fallingVelocity += time * gravity;
        }

        // 0,0 is top left, we want the parabola to go the other way up
        absYLocation = (yLocation * -1) + maxHeight;

        time += 0.1f;
    }

    public void move2() {

        if (xLocation < 0 && yLocation < 0) {
            xLocation = maxWidth/2;
            yLocation = maxHeight/2;
        } else {

            xLocation = xLocation + xVelocity;
            yLocation = yLocation + yVelocity;
            if (xLocation < 0 || xLocation > maxWidth - bubble.getWidth())
                xVelocity = -xVelocity;
            if (yLocation <  0 || yLocation > maxHeight - bubble.getHeight())
                yVelocity = -yVelocity;
        }
        absYLocation = yLocation;
        time += 0.1f;
    }

    public boolean hasMovedOffScreen() {
        return yLocation < 0 || xLocation + bubble.getWidth() < 0 || xLocation > maxWidth;
    }

    public void draw(Canvas canvas) {
        if (isActive) {
            m.reset();
            m.postTranslate(-bubble.getWidth() / 2, -bubble.getHeight() / 2);
            m.postRotate(rotationAngle);
            m.postTranslate(xLocation + (bubble.getWidth() / 2), absYLocation + (bubble.getHeight() / 2));
            rotationAngle += rotationIncrement;

            canvas.drawBitmap(bubble, m, paint);
        } else {
            m.reset();
            m.postTranslate(-bubble.getWidth() / 2, -bubble.getHeight() / 2);
            m.postRotate(rotationAngle);
            m.postTranslate(xLocation + (bubble.getWidth() / 2), absYLocation + (bubble.getHeight() / 2));
            if (id == 0) {
               if(count>5){
                   canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble1_ani3), m, paint);
                   count--;
               }else if(count>4){
                   canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble1_ani4), m, paint);
                   count--;
               }else if(count>3){
                   canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble1_ani5), m, paint);
                   count--;
               }else if(count>2){
                   canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble1_ani6), m, paint);
                   count--;
               }
            }
            else if (id == 1) {
                if(count>5){
                    canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble2_ani3), m, paint);
                    count--;
                }else if(count>4){
                    canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble2_ani4), m, paint);
                    count--;
                }else if(count>3){
                    canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble2_ani5), m, paint);
                    count--;
                }else if(count>2){
                    canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble2_ani6), m, paint);
                    count--;
                }
            }
            else if (id == 2) {
                if(count>5){
                    canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble3_ani3), m, paint);
                    count--;
                }else if(count>4){
                    canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble3_ani4), m, paint);
                    count--;
                }else if(count>3){
                    canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble3_ani5), m, paint);
                    count--;
                }else if(count>2){
                    canvas.drawBitmap(BitmapFactory.decodeResource(view.getResources(), R.drawable.bubble3_ani6), m, paint);
                    count--;
                }
            }
        }
    }

    public Rect getLocation() {
        return new Rect(xLocation, absYLocation, xLocation + bubble.getWidth(), absYLocation + bubble.getHeight());
    }


}
