package com.dc.transmission;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by XIeQian on 2017/3/20.
 */

public class TGLSurfaceView extends GLSurfaceView {
    public MainActivity activity;
    private TRenderer tRenderer;
    public float ratio;

    public TGLSurfaceView(Context context) {
        super(context);
        activity= (MainActivity) context;
        this.setEGLContextClientVersion(2);
        tRenderer=new TRenderer();
        setRenderer(tRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        this.setKeepScreenOn(true);

    }

    @Override
    public boolean onTouchEvent(MotionEvent e)
    {
        return true;
    }

    private class TRenderer implements Renderer{
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0.0f,0.0f,0.0f, 1.0f);
            GLES20.glEnable(GLES20.GL_CULL_FACE);
            GLES20.glEnable(GLES20.GL_DEPTH_TEST);
            MatrixState.setInitStack();
            
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
            ratio = (float) width / height;
            //config the button size
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear( GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);

            drawGameScene();
            drawHUD();
        }

        public void drawGameScene(){
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 40000);
            MatrixState.pushMatrix();

            MatrixState.popMatrix();
        }

        public void drawHUD(){
            MatrixState.pushMatrix();
            MatrixState.setProjectOrtho(-ratio, ratio,-1f,1f,1,10);
            MatrixState.setCamera(0, 0, 0, 0, 0,-1, 0, 1, 0);
            MatrixState.copyMVMatrix();
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

            MatrixState.popMatrix();
        }
    }
}
