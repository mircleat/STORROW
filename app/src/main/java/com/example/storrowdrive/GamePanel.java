package com.example.storrowdrive;
import android.media.MediaPlayer;
import android.view.SurfaceView;
import android.content.Context;
//TODO: UNFINISHED
public class GamePanel extends SurfaceView{

    //Sound Effects
    MediaPlayer trainSound;
    MediaPlayer explosionSound;
    MediaPlayer hopSound;

    //Rhett

    //Rows

    public GamePanel(Context context){
        super(context);

        trainSound = MediaPlayer.create(context, R.raw.train);
        explosionSound = MediaPlayer.create(context, R.raw.explosion);
        hopSound = MediaPlayer.create(context, R.raw.hop);

    }

}
