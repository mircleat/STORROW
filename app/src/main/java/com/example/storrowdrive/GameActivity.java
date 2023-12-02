package com.example.storrowdrive;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
public class GameActivity extends Activity {
    private GamePanel gamepanel;
    private final static long Interval = 30;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        gamepanel = new GamePanel(this);
        setContentView(gamepanel);

    }
}
