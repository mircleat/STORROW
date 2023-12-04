package com.example.storrowdrive;


import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Row extends GameObject{
    private int rowNumber;
    private String rowType;
    private int objectAmount;
    private double velocityRand;

    Decor decors[] = new Decor[2];
    Vehicle vehicle;
    Boat boats[] = new Boat[2];

    Train train;


    public Row(int initializer){
        Random rand = new Random();
        velocityRand = rand.nextDouble();
        X_positionLeft = 0;
        Y_positionTop = (initializer-1) * (ConstantVar.HEIGHT/7);
        rowNumber = (initializer-1);
        rowType = "grass";
        int num = rand.nextInt(2);
        grassMaker(num);
    }

    public void grassMaker(int num){ //makes the decor
        objectAmount = num;
        for(int i = 0; i < num;  i++){
            decors[i] = new Decor();
        }
    }

    public void vehicleMaker(){ //makes the cars
        objectAmount = 1;
        vehicle = new Vehicle(velocityRand);
    }

    public void trainMaker(){ //makes the train
        objectAmount = 1;
        train = new Train(velocityRand);
    }

    public void boatMaker(int num){ //makes the boats
        objectAmount = num;
        for(int i = 0; i < num; i++){
            boats[i] = new Boat(velocityRand, i);
        }
    }

    public void draw(Canvas canvas){ //draws the row
        int unitLen = ConstantVar.HEIGHT/7;
        boolean drawable = false;
        if(Y_positionTop > -1*unitLen && Y_positionTop < 7*unitLen){
            drawable = true;
        }
        if(rowType.equals("grass") && drawable){
            canvas.drawBitmap(ConstantVar.GRASS, 0, Y_positionTop, null);

            for(int i = 0; i < objectAmount; i++){
                decors[i].draw(canvas, Y_positionTop);
            }
        }
        else if(rowType.equals("road") && drawable){
            canvas.drawBitmap(ConstantVar.ROAD, 0, Y_positionTop, null);
            vehicle.draw(canvas, Y_positionTop);
        }
        else if(rowType.equals("water") && drawable){
           canvas.drawBitmap(ConstantVar.WATER, 0, Y_positionTop, null);

           for(int i = 0; i < objectAmount; i++){
               boats[i].draw(canvas, Y_positionTop);
           }
        }
        else if(rowType.equals("train") && drawable){
            canvas.drawBitmap(ConstantVar.TRACKS, 0, Y_positionTop, null);
            train.draw(canvas, Y_positionTop);
        }

    }

    public void shiftDown(){ //shifts the row down
            Y_positionTop += (ConstantVar.HEIGHT / 7); //change down a level
            rowNumber++;
            if (rowNumber >= 8) { //if off screen then get back to the top and randomize type
                Y_positionTop = -1 * (ConstantVar.HEIGHT / 7);
                Random rand = new Random();
                Random rand2 = new Random();
                Random rand3 = new Random();
                rowNumber = -1;
                velocityRand = rand.nextDouble();
                if (rand.nextBoolean()) {
                    rowType = "grass";
                    grassMaker(rand.nextInt(2));
                } else if (rand2.nextBoolean()) {
                    rowType = "road";
                    vehicleMaker();
                } else if (rand3.nextBoolean()) {
                    rowType = "water";
                    boatMaker(rand.nextInt(2)+1);
                } else {
                    rowType = "train";
                    trainMaker();
                }
            }
    }
    public void update(){
        if(rowType.equals("road")){
            vehicle.update();
        }
        else if(rowType.equals("water")){
            for(int i = 0; i < objectAmount; i++){
                boats[i].update();
            }
        }
        else if(rowType.equals("train")){
            train.update();
        }
    }

    public void shiftUp(){

    }

    public boolean collisionCheck(Rhett rhett){
        if(rowType.equals("grass")){ //decor
            for(int i = 0; i < objectAmount; i++){
                if(decors[i].collisionCheck(rhett)){
                    //rhett.center();
                    return false;
                }
            }
        }
        else if(rowType.equals("road")){ //cars
            if(vehicle.collisionCheck(rhett)){
                return true;
            }
        }
        else if(rowType.equals("water")){ //boats
            for(int i = 0; i < objectAmount; i++){
                if(boats[i].collisionCheck(rhett)){
                    rhett.sailing(velocityRand);
                    return false;
                }
            }
            return true;
        }
        else if(rowType.equals("train")){ //train
            if(train.collisionCheck(rhett)){
                return true;
            }
        }
        return false;
    }



}