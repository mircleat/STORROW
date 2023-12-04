package com.example.storrowdrive;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

public class Train extends GameObject{
    Bitmap train;
    private double velocity;

    public Train(double velocityRand){
        this.velocity = velocityRand;
        if(velocity > 0.5){
            train = ConstantVar.TRAMRIGHT;
        }else{
            train = ConstantVar.TRAMLEFT;
        }

        this.X_positionLeft = 0;
    }

    public void draw(Canvas canvas, int Ycoordinate){ //draw the train
        canvas.drawBitmap(train, X_positionLeft, Ycoordinate - ConstantVar.HEIGHT/14, null);
    }

    public void update(){ //update the position of the train
        if(velocity > 0.5){
            if(X_positionLeft >= 3*ConstantVar.WIDTH){
                X_positionLeft = -3*(ConstantVar.WIDTH/5);
            }else{
                X_positionLeft += ConstantVar.WIDTH/10;
            }
        }else{
            if(X_positionLeft <= -9*(ConstantVar.WIDTH/5)){
                X_positionLeft = ConstantVar.WIDTH;
            }else{
                X_positionLeft -= ConstantVar.WIDTH/10;
            }
        }
    };

    public boolean collisionCheck(Rhett rhett){
        if(rhett.X_positionLeft >= X_positionLeft && rhett.X_positionLeft <= X_positionLeft + 5*(ConstantVar.WIDTH/10)){
            return true;
        }
        return false;
    }
}
