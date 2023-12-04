package com.example.storrowdrive;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

public class PauseActivity extends Activity {

    //set bitmap background for pause screen
    //have if you click anywhere go back to game activity
    //stop game loop from the game activity

    private PausePanel pauseView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Set the window to fullscreen mode
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Get the screen size
        DisplayMetrics screenConstants = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(screenConstants);

        pauseView = new PausePanel(ConstantVar.CONTEXT); //"this" should change and make new context var???????????
        setContentView(pauseView);
    }
}
