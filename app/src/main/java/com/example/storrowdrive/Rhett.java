package com.example.storrowdrive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;

public class Rhett extends GameObject{

    int rightCounter = 0;
    int leftCounter = 0;
    int upCounter = 0;
    int downCounter = 0;
    boolean isSailing = false;
    double sailingVelocity;
    Bitmap rhett;
    public Rhett(){
        this.Width = ConstantVar.WIDTH/5;
        this.Height = ConstantVar.HEIGHT/7;
        this.X_positionLeft = 2*(ConstantVar.WIDTH/5);
        this.Y_positionTop = 5*(ConstantVar.HEIGHT/7);
        this.rhett = ConstantVar.RHETT;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(rhett, X_positionLeft, Y_positionTop, null);
    }

    public void update(){
        if(isSailing){
            if(sailingVelocity > 0.5){
                if(X_positionLeft >= ConstantVar.WIDTH){
                    X_positionLeft = -ConstantVar.WIDTH/5;
                }else{
                    X_positionLeft += ConstantVar.WIDTH/100; //in order to have better code, make this 100 a variable speed that you can set given a difficulty. Make it all more general
                }
            }else{
                if(X_positionLeft <= -ConstantVar.WIDTH/5){ //stop rhett from going off screen and from spinning when he movews up more
                    X_positionLeft = ConstantVar.WIDTH;
                }else{
                    X_positionLeft -= ConstantVar.WIDTH/100;
                }
            }
        }
    }

    public void shiftRight(){
        if(X_positionLeft < 4*(ConstantVar.WIDTH/5)){
            X_positionLeft += (ConstantVar.WIDTH/5);
        }
    }

    public void shiftLeft(){
        if(X_positionLeft > 0){
            X_positionLeft -= (ConstantVar.WIDTH/5);
        }
    }

    public void center(){
        X_positionLeft = 2*(ConstantVar.WIDTH/5);
    }

    public void sailing(double velocity){
        isSailing = true;
        sailingVelocity = velocity;
    }

    public void nonSailing(){
        isSailing = false;
    }

    public void shiftUp(){
    }

    public void shiftDown(){
    }

}
