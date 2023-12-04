package com.example.storrowdrive;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

public class Boat extends GameObject{
    Bitmap boat;
    private double velocity;

    public Boat(double velocityRand, int initializer){
        this.velocity = velocityRand;
        if(velocity > 0.5){
            boat = ConstantVar.BOATRIGHT;
        }else{
            boat = ConstantVar.BOATLEFT;
        }
        Random rand = new Random();
        if(initializer == 0) {
            int bound = rand.nextInt(2); // 1st or 2nd sq
            this.X_positionLeft = bound * (ConstantVar.WIDTH / 5);
        }else {
            int bound2 = rand.nextInt(2) + 3; // 3rd, 4th, 5th square
            this.X_positionLeft = bound2 * (ConstantVar.WIDTH / 5);
        }
    }

    public void draw(Canvas canvas, int Ycoordinate){
        canvas.drawBitmap(boat, X_positionLeft, Ycoordinate, null);
    }

    public void update(){ //udpate the position of the boat
        if(velocity > 0.5){
            if(X_positionLeft >= ConstantVar.WIDTH){
                X_positionLeft = -ConstantVar.WIDTH/5;
            }else{
                X_positionLeft += ConstantVar.WIDTH/100;
            }
        }else{
            if(X_positionLeft <= -ConstantVar.WIDTH/5){
                X_positionLeft = ConstantVar.WIDTH;
            }else{
                X_positionLeft -= ConstantVar.WIDTH/100;
            }
        }
    }

    public boolean collisionCheck(Rhett rhett){ //check if rhett is in the same square as the boat
        if(rhett.X_positionLeft >= X_positionLeft && rhett.X_positionLeft <= X_positionLeft + 2*(ConstantVar.WIDTH/5)){
            return true;
        }
        return false;
    }

}

