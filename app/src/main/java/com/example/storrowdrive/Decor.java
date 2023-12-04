package com.example.storrowdrive;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import java.util.Random;

public class Decor {

    Bitmap decor;
    int Xcoordinate;

    public Decor(){
        Random rand = new Random();
        int randNum = rand.nextInt(4);
        if(randNum == 2){
            randNum = 4;
        }
        Xcoordinate = randNum * (ConstantVar.WIDTH/5);
        switch(randNum){
            case 0:
                decor = ConstantVar.TREE;
                break;
            case 1:
                decor = ConstantVar.TREE; //keep for when we add more decor like chairs
                break;
            case 2:
                decor = ConstantVar.TREE;
                break;
            default:
                decor = ConstantVar.TREE;
                break;
        }
    }

    public void draw(Canvas canvas, int Ycoordinate){ //draw the decor
        canvas.drawBitmap(decor, Xcoordinate, Ycoordinate, null);
    }

    public boolean collisionCheck(Rhett rhett){ //check if rhett is in the same X coordinate as the decor
        if(rhett.X_positionLeft >= Xcoordinate && rhett.X_positionLeft <= Xcoordinate + (ConstantVar.WIDTH/10)){
            rhett.center();
            return true;
        }
        return false;
    }
}
