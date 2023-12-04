package com.example.storrowdrive;

import android.graphics.Canvas;
public class GameObject {
    //Object's position
    protected int X_positionLeft;
    protected int Y_positionTop;

    //Width and Height of BitMap
    protected int Height;
    protected int Width;

    //Constructor
    public GameObject(){}

    //Get Functions
    protected int get_X(){
        return X_positionLeft;
    }
    protected int get_Y(){
        return Y_positionTop;
    }
    protected int get_Height(){
        return Height;
    }
    protected int get_Width(){
        return Width;
    }

    //Draw and Update Functions
    protected void draw(Canvas canvas){}
    protected void update(){}

}
