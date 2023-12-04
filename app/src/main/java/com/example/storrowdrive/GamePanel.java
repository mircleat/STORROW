package com.example.storrowdrive;
import android.media.MediaPlayer;
import android.view.SurfaceView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.graphics.Canvas;
import android.content.Intent;
import android.view.SurfaceHolder;
import androidx.annotation.NonNull;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private Rhett rhett;
    private float oldX = 0, oldY = 0, newX = 0, newY = 0;
    private static int SWIPE_THRESHOLD = 100; //minimum distance for a swipe to be registered
    private GameThread gameThread;
    boolean collided = false; //set when the user collides with a death obstacle
    public int currentRow = 6; //the row that rhett is currently on
    private int counter = 0;

    //Rows
    private Row rows[] = new Row[9]; //9 rows, 7 of which are visible at a time, 1 offscreen in each direcction

    private int shiftCounter = 0; //Keep  in case we want to show the user's score in the future
    boolean setExploded = false; //set when the user collides with a death obstacle
    boolean setGameOver = false; //set when the user explodes to delay the screen restart

    //Pause Button
    private Bitmap pauseButton; //the pause button
    private int pauseButtonX;
    private int pauseButtonY;
    private boolean pauseButtonPressed = false;

    public GamePanel(Context context){
        super(context);

        getHolder().addCallback(this); //intercept events
        setFocusable(true); //set the panel as the focus of the screen

        //Set the sound effects
        ConstantVar.HOP = MediaPlayer.create(ConstantVar.CONTEXT, R.raw.hop);
        ConstantVar.EXPLOSION = MediaPlayer.create(ConstantVar.CONTEXT, R.raw.explosion);
        ConstantVar.HORN = MediaPlayer.create(ConstantVar.CONTEXT, R.raw.train);

        //Set the bitmaps for the objects
        Bitmap tree = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.tree);
        ConstantVar.TREE = Bitmap.createScaledBitmap(tree, ConstantVar.WIDTH/5, ConstantVar.HEIGHT/7, false);
        Bitmap carRight = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.carright);
        ConstantVar.CARRIGHT = Bitmap.createScaledBitmap(carRight, ConstantVar.WIDTH/5, ConstantVar.HEIGHT/7, false);
        Bitmap carLeft = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.carleft);
        ConstantVar.CARLEFT = Bitmap.createScaledBitmap(carLeft, ConstantVar.WIDTH/5, ConstantVar.HEIGHT/7, false);
        Bitmap busRight = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.busright);
        ConstantVar.BUSRIGHT = Bitmap.createScaledBitmap(busRight, 2*(ConstantVar.WIDTH/5), ConstantVar.HEIGHT/7, false);
        Bitmap busLeft = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.busleft);
        ConstantVar.BUSLEFT = Bitmap.createScaledBitmap(busLeft, 2*(ConstantVar.WIDTH/5), ConstantVar.HEIGHT/7, false);
        Bitmap tramRight = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.tramright);
        ConstantVar.TRAMRIGHT = Bitmap.createScaledBitmap(tramRight, 3* (ConstantVar.WIDTH/5), 2*(ConstantVar.HEIGHT/7), false);
        Bitmap tramLeft = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.tramleft);
        ConstantVar.TRAMLEFT = Bitmap.createScaledBitmap(tramLeft, 3*(ConstantVar.WIDTH/5), 2*(ConstantVar.HEIGHT/7), false);
        Bitmap boatRight = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.rightboat);
        ConstantVar.BOATRIGHT = Bitmap.createScaledBitmap(boatRight, 2*(ConstantVar.WIDTH/5), ConstantVar.HEIGHT/7, false);
        Bitmap boatLeft = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.leftboat);
        ConstantVar.BOATLEFT = Bitmap.createScaledBitmap(boatLeft, 2*(ConstantVar.WIDTH/5), ConstantVar.HEIGHT/7, false);
        Bitmap water = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.water);
        ConstantVar.WATER = Bitmap.createScaledBitmap(water, ConstantVar.WIDTH, (ConstantVar.HEIGHT/7), false);
        Bitmap road = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.road);
        ConstantVar.ROAD = Bitmap.createScaledBitmap(road, ConstantVar.WIDTH, (ConstantVar.HEIGHT/7), false);
        Bitmap grass = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.grass);
        ConstantVar.GRASS = Bitmap.createScaledBitmap(grass, ConstantVar.WIDTH, (ConstantVar.HEIGHT/7), false);
        Bitmap tracks = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.track);
        ConstantVar.TRACKS = Bitmap.createScaledBitmap(tracks, ConstantVar.WIDTH, (ConstantVar.HEIGHT/7), false);
        Bitmap rhettBitmap = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.rhett);
        ConstantVar.RHETT = Bitmap.createScaledBitmap(rhettBitmap, ConstantVar.WIDTH/5, ConstantVar.HEIGHT/7, false);
        Bitmap rhettMove = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.rhettnolegs);
        ConstantVar.RHETTMOVE = Bitmap.createScaledBitmap(rhettMove, ConstantVar.WIDTH/5, ConstantVar.HEIGHT/7, false);
        Bitmap explosion = BitmapFactory.decodeResource(ConstantVar.CONTEXT.getResources(), R.drawable.exploded);
        ConstantVar.EXPLOSIONIMAGE = Bitmap.createScaledBitmap(explosion, ConstantVar.WIDTH/5, ConstantVar.HEIGHT/7, false);
        pauseButton = BitmapFactory.decodeResource(getResources(), R.drawable.button_pause);
        ConstantVar.PAUSEBUTTON = Bitmap.createScaledBitmap(pauseButton, (ConstantVar.WIDTH/5), (ConstantVar.HEIGHT/7), false);


        //Make Rows
        for(int i = 8; i >= 0 ; i--){
            rows[i] = new Row(i);
        }

        //Make Rhett
        rhett = new Rhett();

        //Make Pause Button
        pauseButtonX = 10;
        pauseButtonY = 10;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event){

        if(event.getAction() == MotionEvent.ACTION_DOWN){ //if user presses down

            //CHECK IF PAUSE BUTTON HAS BEEN PRESSED
            if(event.getX() >= pauseButtonX && event.getX() <= pauseButtonX + pauseButton.getWidth() && event.getY() >= pauseButtonY && event.getY() <= pauseButtonY + pauseButton.getHeight()){
                pauseButtonPressed = true;
                //return true; //don't do anything else
            }
            else{
                //pauseButtonPressed = false;
                //return false; //which of these stope it?
            }
        }

        //CHECK SWIPE
        switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: //initial press
                    oldX = event.getX();
                    oldY = event.getY();
                    break;
                case MotionEvent.ACTION_UP: //release
                    newX = event.getX();
                    newY = event.getY();

                    float dx = newX - oldX; //change in x
                    float dy = newY - oldY; //change in y

                    if (Math.abs(dx) > SWIPE_THRESHOLD) {
                        ConstantVar.HOP.start();
                        if (dx > 0) {
                            //swipe right
                            shiftRight();
                            update();
                        } else {
                            //swipe left
                            shiftLeft();
                            update();
                        }
                    }else if (Math.abs(dy) > SWIPE_THRESHOLD) {
                        ConstantVar.HOP.start();
                        if (dy > 0) {
                            //swipe down
                            shiftUp();
                            update();
                        } else {
                            //swipe up
                            shiftDown();
                            update();
                        }
                    }
                    break;
        }
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Draw Rows
        for (int i = 0; i < 9; i++) {
            rows[i].draw(canvas);
        }

        //Draw the pause button
        canvas.drawBitmap(ConstantVar.PAUSEBUTTON, pauseButtonX, pauseButtonY, null);

        //Draw Rhett
        rhett.draw(canvas);

        //Draw the pause menu if the pause button is pressed
        if (pauseButtonPressed)
        {
            pauseButtonPressed = false;
            gameThread.setRunning(false);
            Intent IntentPause = new Intent(ConstantVar.CONTEXT, PauseActivity.class);
            IntentPause.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            ConstantVar.CONTEXT.startActivity(IntentPause);
        }

        //Draw the explosion if the user has collided with a death obstacle
        if(setExploded){
                canvas.drawBitmap(ConstantVar.EXPLOSIONIMAGE, rhett.X_positionLeft, rhett.Y_positionTop, null);
                setGameOver = true;
        }
    }



    public void update(){
        //Check if rhett has collided with a death obstacle and run related logic
        collided = rows[currentRow].collisionCheck(rhett);
        //if true than destroy rhett and call endgame
        if(collided){
            collided = false;
            setExploded = true;
        }
        if(setGameOver){
            Intent IntentRestart = new Intent(ConstantVar.CONTEXT, MainActivity.class); //Go back to mainActivity
            IntentRestart.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            ConstantVar.CONTEXT.startActivity(IntentRestart);
            gameThread.setRunning(false); //stop the game thread
            gameThread.interrupt();
        }

        //Update the rows
        for(int i = 0; i < 9; i++){
            rows[i].update();
        }

        //Update Rhett
        rhett.update();
    }

    public void shiftUp(){
        //Shift the rows up in case we want to implement rhett moving down in the future
        for(int i = 0; i < 9; i++){
            rows[i].shiftUp();
        }
    }

    public void shiftDown(){
        //Shift the rows down
        shiftCounter++; //increment the shift counter (score)
        currentRow--;
        if(currentRow == -1){
            currentRow = 8;
        }
        for(int i = 0; i < 9; i++){
            rows[i].shiftDown();
        }
        //make Rhett stop sailing if he was on a boat
        rhett.nonSailing();
    }

    public void shiftRight(){
        rhett.shiftRight();
    }

    public void shiftLeft(){
        rhett.shiftLeft();
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder){
        gameThread = new GameThread(getHolder(), this); //Create game thread on surface creation
        gameThread.setRunning(true);
        gameThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height){}

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder){}
}
