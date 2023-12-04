package com.example.storrowdrive;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

public class Vehicle extends GameObject{
    Bitmap vehicle;
    private double velocity;

    public Vehicle(double velocityRand){
        this.velocity = velocityRand;
        if(velocity > 0.5){//Decide which direction the car is going
            vehicle = ConstantVar.CARRIGHT;
        }else{
            vehicle = ConstantVar.CARLEFT;
        }
        Random rand = new Random();
        int bound = rand.nextInt(5); //Have it start at a random spot
        this.X_positionLeft = bound * (ConstantVar.WIDTH / 5);
    }

    public void draw(Canvas canvas, int Ycoordinate){
        canvas.drawBitmap(vehicle, X_positionLeft, Ycoordinate, null);
    }

    public void update(){
        if(velocity > 0.5){
            if(X_positionLeft >= ConstantVar.WIDTH){
                X_positionLeft = -ConstantVar.WIDTH/5; //if offscreen loop around
            }else{
                X_positionLeft += ConstantVar.WIDTH/100; //move the car a small amount each run()
            }
        }else{
            if(X_positionLeft <= -ConstantVar.WIDTH/5){
                X_positionLeft = ConstantVar.WIDTH;
            }else{
                X_positionLeft -= ConstantVar.WIDTH/100;
            }
        }
    }

    public boolean collisionCheck(Rhett rhett){
        if(rhett.X_positionLeft >= X_positionLeft && rhett.X_positionLeft <= X_positionLeft + (ConstantVar.WIDTH/10)){
            return true;
        }
        return false;
    }
}
