package com.example.hiyoriaya.blockball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by hiyoriaya on 2016/01/21.
 */
public class Pad implements DrawableItem{
    private final float mTop;
    private float mLeft;
    private final float mBottom;
    private float mRight;

    public Pad(float top,float bottom){
        mTop = top;
        mBottom = bottom;
    }

    public void setLeftRight(float left,float right){
        mLeft = left;
        mRight = right;

    }

    public void draw(Canvas canvas,Paint paint){

        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(mLeft,mTop,mRight,mBottom,paint);
    }
}
