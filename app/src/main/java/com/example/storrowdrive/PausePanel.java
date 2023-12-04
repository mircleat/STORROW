package com.example.storrowdrive;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import androidx.annotation.NonNull;

public class PausePanel extends SurfaceView implements SurfaceHolder.Callback
{
    Bitmap paused;

    private PauseThread pauseThread;
    public PausePanel(Context context)
    {
        super(context);
        getHolder().addCallback(this); //Register the Panel class as a SurfaceHolder callback listener
        setFocusable(true); //Make the Panel the focus of the screen since it handles user input

        //Set the background
        Bitmap PauseScreen = BitmapFactory.decodeResource(getResources(), R.drawable.gamepaused);
        paused = Bitmap.createScaledBitmap(PauseScreen, ConstantVar.WIDTH, ConstantVar.HEIGHT, false);
    }
    @Override //from SurfaceHolder
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height){}

    @Override //from SurfaceHolder
    public void surfaceCreated(@NonNull SurfaceHolder holder){
        pauseThread = new PauseThread(getHolder(),this);
        pauseThread.setRunning(true);
        pauseThread.start();
    }

    @Override //from SurfaceHolder
    public boolean onTouchEvent(MotionEvent event){
        pauseThread.setRunning(false); //Stop the thread
        pauseThread.interrupt();
        Intent intent = new Intent(ConstantVar.CONTEXT, GameActivity.class); //Create an intent to start the GameActivity
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        ConstantVar.CONTEXT.startActivity(intent);

        return true;
    }

    @Override //from SurfaceHolder
    public void surfaceDestroyed(@NonNull SurfaceHolder holder){}

    public void update(){}

    @Override //from SurfaceView
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(paused, 0, 0, null);
    }
}