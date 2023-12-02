package com.example.storrowdrive;

import android.provider.Settings;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
public class GameThread extends Thread {
    public static final int FPSmax = 60;
    private double FPSave;
    private SurfaceHolder surfaceHolder;

    private Panel screenPanel;

    private boolean isRunning = false;

    private Canvas canvas;


    public GameThread(SurfaceHolder surfaceHolder, Panel panel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.screenPanel = panel;
    }

    public void setRunning(boolean runSource) {
        this.isRunning = runSource;
    }

    public void startThread() {
        this.isRunning = true;
        this.start();
    }

    @Override
    public void run() {
        super.run();
        long startTime;
        long timeMillis = 1000/FPSmax;
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/FPSmax;

        while(isRunning)
        {
            startTime = System.nanoTime();
            canvas = null;

            try
            {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder)
                {
                    this.screenPanel.update();
                    this.screenPanel.draw(canvas);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                if(canvas != null)
                {
                    try
                    {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - timeMillis;
            try
            {
                if(waitTime > 0)
                {
                    this.sleep(waitTime);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            frameCount++;
            if(frameCount == FPSmax)
            {
                FPSave = 1000/((totalTime/frameCount) / 1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.println(FPSave);
            }
        }
    }
}
