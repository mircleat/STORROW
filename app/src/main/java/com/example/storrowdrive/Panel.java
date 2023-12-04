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

/*
    Panel is a management class that dictates the logic, rendering, and input for the Start Screen
    SurfaceView is a class used to draw and update the user IO. We use it because it allows Double Buffer and implementation of a callback interface
    SurfaceHolder is a interface used to manage SurfaceViews by locking and unlocking the Canvas
 */
public class Panel extends SurfaceView implements SurfaceHolder.Callback
{
    Bitmap background;
    private MainThread thread;

    public Panel(Context context)
    {
        super(context);
        getHolder().addCallback(this); //Register the Panel class as a SurfaceHolder callback listener
        ConstantVar.CONTEXT = context; //Everytime the Game is instantiated, a MainActivity context is created
        setFocusable(true); //Make the Panel the focus of the screen since it handles user input

        //Set the background
        Bitmap OG = BitmapFactory.decodeResource(getResources(), R.drawable.startup);
        background = Bitmap.createScaledBitmap(OG, ConstantVar.WIDTH, ConstantVar.HEIGHT, false);
    }

    @Override //from SurfaceHolder
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height){}

    @Override //from SurfaceHolder
    public void surfaceCreated(@NonNull SurfaceHolder holder){ //Create the first Surface
        thread = new MainThread(getHolder(),this); //Create the thread for gamelooping
        thread.setRunning(true);
        thread.start();
    }

    @Override //from SurfaceHolder
    public void surfaceDestroyed(@NonNull SurfaceHolder holder){}

    @Override //from SurfaceHolder
    public boolean onTouchEvent(MotionEvent event){
        thread.setRunning(false); //Stop the thread
        thread.interrupt();
        Intent intent = new Intent(ConstantVar.CONTEXT, GameActivity.class); //Create an intent to start the GameActivity
        ConstantVar.CONTEXT.startActivity(intent);

        return true;
    }

    public void update(){} //Keep in case of Start Screen Animations in the future

    @Override //from SurfaceView
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(background, 0, 0, null); //Draw the background
    }
}
