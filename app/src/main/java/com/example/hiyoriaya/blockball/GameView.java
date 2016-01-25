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
        private ArrayList<DrawableItem> mItemList;
        private Pad mPad;
        private float mPadHalfWidth;
        private Ball mBall;
        private float mBallRadius;

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
                    long startTime = System.currentTimeMillis();
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
                        float padLeft = mTouchedX - mPadHalfWidth;
                        float padRight = mTouchedX + mPadHalfWidth;
                        mPad.setLeftRight(padLeft,padRight);
                        mBall.move();

                        //当たり判定処理
                        float ballTop = mBall.getY()-mBallRadius;
                        float ballLeft = mBall.getX()-mBallRadius;
                        float ballBottom = mBall.getY() + mBallRadius;
                        float ballRight = mBall.getX() + mBallRadius;
                        if(ballLeft<0 && mBall.getSpeedX()<0 || ballRight >= getWidth() && mBall.getSpeedX()>0){
                            mBall.setSpeedX(-mBall.getSpeedX());    //横反転
                        }
                        if(ballTop<0 || ballBottom>getHeight()){
                            mBall.setSpeedY((-mBall.getSpeedY()));  //縦反転
                        }

                        for (DrawableItem item : mItemList) {
                            item.draw(canvas, paint);
                        }
                        unlockCanvasAndPost(canvas);
                        long sleeptime = 16 -(System.currentTimeMillis() - startTime);
                        if(sleeptime > 0){
                            try{
                                Thread.sleep(sleeptime);
                            }catch(InterruptedException e){}
                        }
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
        //ブロック生成
        float blockWidth = width/10;
        float blockHeight = height/20;
        mItemList = new ArrayList<DrawableItem>();
        for(int i=0;i<100;i++){
            float blockTop = (i/10) * blockHeight;
            float blockLeft = (i%10) * blockWidth;
            float blockBottom = blockTop + blockHeight;
            float blockRight = blockLeft + blockWidth;
            mItemList.add(new Block(blockTop,blockLeft,blockBottom,blockRight));
        }

        //パッド生成
        mPad = new Pad(height * 0.8f,height * 0.85f);
        mPadHalfWidth = width / 10;
        mItemList.add(mPad);

        //ボール生成
        mBallRadius = width < height ? width / 40 :height /40;
        mBall = new Ball(width/2,height/2,mBallRadius);
        mItemList.add(mBall);
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
