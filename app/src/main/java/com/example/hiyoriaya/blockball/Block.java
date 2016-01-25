package com.example.hiyoriaya.blockball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by hiyoriaya on 2016/01/20.
 * ブロックデータ
 */
public class Block implements DrawableItem{
    //普遍の項目だけfinalをつける
    private final float mTop;
    private final float mLeft;
    private final float mBottom;
    private final float mRight;
    private int mHard;

    public Block(float top,float left,float bottom,float right){
        mTop = top;
        mLeft = left;
        mBottom = bottom;
        mRight = right;
        mHard = 1;
    }

    //ブロックを描写するやつ
    public void draw(Canvas canvas,Paint paint){
        if(mHard > 0){
            //耐久がまだあるとき

            //ブロック定義
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRect(mLeft, mTop, mRight, mBottom, paint);

            //ブロックの枠線？
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
            paint.setStrokeWidth(4f);//線の幅
            canvas.drawRect(mLeft,mTop,mRight,mBottom,paint);
        }
    }

}
