package com.dc.transmission.glCore;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.dc.transmission.MainActivity;
import com.dc.transmission.glObjects.MoveController;
import com.dc.transmission.glObjects.Portal;
import com.dc.transmission.glObjects.PortalController;
import com.dc.transmission.glObjects.Role;
import com.dc.transmission.glObjects.WallsManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.dc.transmission.gameData.Constant.VEL_SCALE;

/**
 * Created by XIeQian on 2017/3/20.
 */

public class TGLSurfaceView extends GLSurfaceView {
    public MainActivity activity;
    private TRenderer tRenderer;
    public float ratio;

    private MoveController moveController;
    private PortalController portalController;


    public TGLSurfaceView(Context context) {
        super(context);
        activity= (MainActivity) context;
        this.setEGLContextClientVersion(2);
        tRenderer=new TRenderer();
        setRenderer(tRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        this.setKeepScreenOn(true);

        moveController=new MoveController(new RectF(80,420,320,660));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveController.onTouchEvent(event);
        portalController.onTouchEvent(event);
        if(moveController.getClickDown()) {
            tRenderer.setRoleVel(moveController.getMoveVector());
        }
        if(portalController.getClicked()){

        }
        return true;
    }

    private class TRenderer implements Renderer{
        private Role role;
        private WallsManager wallsManager;
        private Portal[] portals;

        public TRenderer(){
            portals=new Portal[2];
            wallsManager=new WallsManager();
            role=new Role();
            portals[0]=new Portal();
            portals[1]=new Portal();
        }

        public void setRoleVel(float[] vel){
            float[] vel3=new float[3];
            float[] rcam=role.getCam();
            vel3[1]=-9.8f * VEL_SCALE;
            //need to fix
            vel3[0]=(rcam[0]*vel[0])*VEL_SCALE;
            vel3[2]=(rcam[0]*vel[0])*VEL_SCALE;
            //---
            role.setVel(vel3);
        }

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

            gameLogic();
            drawGameScene();
            drawHUD();
        }

        private void drawGameScene(){
            MatrixState.setProjectFrustum(-ratio, ratio, -1, 1, 3, 40000);
            MatrixState.pushMatrix();

            wallsManager.draw(0);
            role.draw(0);


            MatrixState.popMatrix();
        }

        private void drawHUD(){
            MatrixState.pushMatrix();
            MatrixState.setProjectOrtho(-ratio, ratio,-1f,1f,1,10);
            MatrixState.setCamera(0, 0, 0, 0, 0,-1, 0, 1, 0);
            MatrixState.copyMVMatrix();
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

            MatrixState.popMatrix();
        }

        private void gameLogic(){
            wallsManager.calcCollision(role.getPos(),role.getVel());
            role.runLogic();
        }
    }
}
