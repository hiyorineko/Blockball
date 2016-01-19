package com.example.hiyoriaya.blockball;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SurfaceTexture;
import android.view.TextureView;

/**
 * Created by hiyoriaya on 2016/01/19.
 * ゲーム画面描写用のTextureView
 */
public class GameView extends TextureView implements TextureView.SurfaceTextureListener{

    public GameView(Context context){
        super(context); //親クラスからcontextを受ける
        setSurfaceTextureListener(this);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Canvas canvas = lockCanvas();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(300,300,200,paint);
        unlockCanvasAndPost(canvas);
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {

    }

}
