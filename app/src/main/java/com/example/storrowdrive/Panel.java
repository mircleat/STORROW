package com.example.storrowdrive;

import static androidx.core.content.ContextCompat.startActivity;

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

    private GameThread thread;


    public Panel(Context context)
    {
        super(context);
        getHolder().addCallback(this); //Register the Panel class as a SurfaceHolder callback listener
        ConstantVar.CONTEXT = context; //Everytime the Game is instantiated, a MainActivity context is created
        thread = new GameThread(getHolder(), this); //Create a thread
        setFocusable(true); //Make the Panel attentive to user input

        //Set the background
        Bitmap OG = BitmapFactory.decodeResource(getResources(), R.drawable.startup);
        background = Bitmap.createScaledBitmap(OG, ConstantVar.WIDTH, ConstantVar.HEIGHT, false);
    }

    @Override //from SurfaceHolder
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height){}

    @Override //from SurfaceHolder
    public void surfaceCreated(@NonNull SurfaceHolder holder){ //Create the first Surface
        thread = new GameThread(getHolder(),this);
        thread.setRunning(true);
        thread.start();
    }

    @Override //from SurfaceHolder
    public void surfaceDestroyed(@NonNull SurfaceHolder holder){}

    @Override //from SurfaceHolder
    public boolean onTouchEvent(MotionEvent event){

        startGameActivity();
        return true;
    }

    public void startGameActivity(){
        Intent intent = new Intent(ConstantVar.CONTEXT, GameActivity.class);
        ConstantVar.CONTEXT.startActivity(intent);
    }

    public void update(){}

    @Override //from SurfaceView
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawBitmap(background, 0, 0, null);
    }
}
