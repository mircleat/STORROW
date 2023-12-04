package com.example.storrowdrive;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class PauseThread extends Thread{
    public static final int FPSmax = ConstantVar.MAXFPS;
    private double FPSave;
    private SurfaceHolder surfaceHolder;
    private PausePanel screenPausePanel;
    private boolean isRunning = false;
    private Canvas canvas;


    public PauseThread(SurfaceHolder surfaceHolder, PausePanel pause) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.screenPausePanel = pause;
    }

    public void setRunning(boolean runSource) {
        this.isRunning = runSource;
    }
    @Override
    public void run() {
        super.run();
        long startTime;
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
                    this.screenPausePanel.update();
                    this.screenPausePanel.draw(canvas);
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
            waitTime = targetTime - (System.nanoTime() - startTime)/1000000;
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

