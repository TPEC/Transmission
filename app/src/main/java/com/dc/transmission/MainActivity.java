package com.dc.transmission;

import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.threed.jpct.util.AAConfigChooser;

import java.lang.reflect.Field;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLDisplay;

public class MainActivity extends AppCompatActivity {
    public static MainActivity master=null;
    private GameDatabase gd=GameDatabase.getInstance();

    private GLSurfaceView glSv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if(master!=null){
            copy(master);
        }
        gd.setResources(getResources());
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gd.setMainActivity(this);
        glSv=new GLSurfaceView(getApplication());
        //ogles2
        glSv.setEGLContextClientVersion(2);
        //anti-aliasing
        glSv.setEGLConfigChooser(new AAConfigChooser(glSv));

        glSv.setEGLConfigChooser(new GLSurfaceView.EGLConfigChooser() {
            @Override
            public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
                int[] attributes = new int[] { EGL10.EGL_DEPTH_SIZE, 16, EGL10.EGL_NONE };
                EGLConfig[] configs = new EGLConfig[1];
                int[] result = new int[1];
                egl.eglChooseConfig(display, attributes, configs, 1, result);
                return configs[0];
            }
        });
        glSv.setRenderer(gd.getRenderer());
        setContentView(glSv);
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSv.onPause();
    }

    @Override
    protected void onResume(){
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        super.onResume();
        glSv.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return super.onTouchEvent(event);
    }

    private void copy(Object src) {
        try {
            Field[] fs = src.getClass().getDeclaredFields();
            for (Field f : fs) {
                f.setAccessible(true);
                f.set(this, f.get(src));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
