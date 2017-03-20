package com.dc.transmission;

import android.content.pm.ActivityInfo;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.WindowManager;

import static com.dc.transmission.gameData.Constant.SCREEN_HEIGHT;
import static com.dc.transmission.gameData.Constant.SCREEN_WIDTH;
import static com.dc.transmission.gameData.Constant.ratio_height;
import static com.dc.transmission.gameData.Constant.ratio_width;

public class MainActivity extends AppCompatActivity {
    TGLSurfaceView tglSV;
    Handler handler;
    SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tglSV=new TGLSurfaceView(this);
        initScreen();
        initHandler();
        initSound();
        initDatabase();

    }

    public void initScreen(){
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int tempHeight=(int) (SCREEN_HEIGHT=dm.heightPixels);
        int tempWidth=(int) (SCREEN_WIDTH=dm.widthPixels);
        if(tempHeight<tempWidth)
        {
            SCREEN_HEIGHT=tempHeight;
            SCREEN_WIDTH=tempWidth;
        }
        else
        {
            SCREEN_HEIGHT=tempWidth;
            SCREEN_WIDTH=tempHeight;
        }
        ratio_width=SCREEN_WIDTH/800;
        ratio_height=SCREEN_HEIGHT/480;
    }
    public void initHandler(){
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case 1:

                        break;
                }
            }
        };
    }
    public void initSound(){

    }
    public void initDatabase(){

    }

    @Override
    protected void onPause() {
        super.onPause();
        tglSV.onPause();
    }

    @Override
    protected void onResume(){
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onResume();
        tglSV.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
