package com.example.hiyoriaya.blockball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by hiyoriaya on 2016/01/19.
 * ゲーム画面描写用のTextureView
 */
public class GameView extends TextureView implements View.OnTouchListener,TextureView.SurfaceTextureListener{
        private Thread mThread;
        volatile private boolean mIsRunnable;
        volatile private float mTouchedX;
        volatile private float mTouchedY;
        private ArrayList<Block> mBlockList;

    public GameView(Context context){
        super(context); //親クラスからcontextを受ける
        setSurfaceTextureListener(this);
        setSurfaceTextureListener(this);
        setOnTouchListener(this);
    }

    public void start(){
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    synchronized (GameView.this) {
                        if (!mIsRunnable) {
                            break;
                        }

                        Canvas canvas = lockCanvas();
                        Paint paint = new Paint();
                        if (canvas == null) {
                            continue;
                        }
                        canvas.drawColor(Color.BLACK);
                        for (Block item : mBlockList) {
                            item.draw(canvas, paint);
                        }
                        unlockCanvasAndPost(canvas);
                    }
                }
            }
        });
        mIsRunnable = true;
        mThread.start();
    }

    public void stop(){
        mIsRunnable = false;
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        readyObjects(width,height);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        readyObjects(width,height);
    }


    public void readyObjects(int width,int height){
        float blockWidth = width/10;
        float blockHeight = height/20;
        mBlockList = new ArrayList<Block>();
        for(int i=0;i<100;i++){
            float blockTop = (i/10) * blockHeight;
            float blockLeft = (i%10) * blockWidth;
            float blockBottom = blockTop + blockHeight;
            float blockRight = blockLeft + blockWidth;
            mBlockList.add(new Block(blockTop,blockLeft,blockBottom,blockRight));
        }
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        synchronized (this) {
            return true;
        }
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mTouchedX = event.getX();
        mTouchedY = event.getY();
        return true;
    }
}
