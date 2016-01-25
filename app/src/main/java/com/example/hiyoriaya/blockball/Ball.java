package com.example.hiyoriaya.blockball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by hiyoriaya on 2016/01/25.
 */
public class Ball implements DrawableItem{
    private float mX;
    private float mY;
    private float mSpeedX;
    private float mSpeedY;
    private final float mRadius;

    public Ball(float x,float y,float radius){
        mX = x;
        mY = y;
        mRadius = radius;
        mSpeedX = radius/5;
        mSpeedY = radius/5;
    }

    public void move(){
        mX += mSpeedX;
        mY += mSpeedY;
    }

    public float getSpeedX(){
        return mSpeedX;
    }
    public float getSpeedY(){
        return mSpeedY;
    }
    public float getX(){
        return mX;
    }
    public float getY(){
        return mY;
    }

    public void setSpeedX(float speedX){
        mSpeedX = speedX;
    }

    public void setSpeedY(float speedY){
        mSpeedY = speedY;
    }

    public void draw(Canvas canvas,Paint paint){
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mX,mY,mRadius,paint);
    }
}
