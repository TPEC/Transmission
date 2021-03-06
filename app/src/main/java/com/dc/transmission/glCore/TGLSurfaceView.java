package com.dc.transmission.glCore;

import android.content.Context;
import android.graphics.RectF;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.dc.transmission.MainActivity;
import com.dc.transmission.gameData.TexFactory;
import com.dc.transmission.gameData.VecFactory;
import com.dc.transmission.glObjects.MoveController;
import com.dc.transmission.glObjects.Portal;
import com.dc.transmission.glObjects.PortalController;
import com.dc.transmission.glObjects.Role;
import com.dc.transmission.glObjects.ViewController;
import com.dc.transmission.glObjects.WallsManager;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.dc.transmission.gameData.Constant.CAM_SCALE;
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
    private ViewController viewController;


    public TGLSurfaceView(Context context) {
        super(context);
        TexFactory.glSv=this;
        activity= (MainActivity) context;

        this.setEGLContextClientVersion(2);
        tRenderer=new TRenderer();
        setRenderer(tRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        this.setKeepScreenOn(true);

        RectF rectMC=new RectF(80,420,320,660);
        moveController=new MoveController(rectMC);
        RectF rectPC=new RectF();
        viewController=new ViewController(new RectF(0,0,1280,720),new RectF[]{rectMC,rectPC});
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        moveController.onTouchEvent(event);
//        portalController.onTouchEvent(event);
        viewController.onTouchEvent(event);
        if(moveController.getClickDown()) {
            tRenderer.setRoleVel(moveController.getMoveVector());
        }
//        if(portalController.getClicked()){
//
//        }
        if(viewController.getClickDown()){
            tRenderer.setRoleCam(viewController.getMoveVector());
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

            wallsManager.initTexture();
        }

        public void setRoleVel(float[] vVel){
            float[] vec3=VecFactory.crossProduct(role.getCam(),role.getCamh());
            float[] vec2=new float[2];
            if(vec3[0]!=0){
                vec2[1]=1;
                vec2[0]=-vec3[2]/vec3[0];
            }else{
                vec2[0]=1;
                vec2[1]=-vec3[0]/vec3[2];
            }
            float k=vec2[0]*role.getCam()[0]+vec2[1]*role.getCam()[2];
            if(k>0) {
                VecFactory.multiply2(vec2, -1f);
            }else if(k==0){
                if(vec2[0]*role.getCamh()[0]+vec2[1]*role.getCamh()[2]<0)
                    VecFactory.multiply2(vec2, -1f);
            }
            VecFactory.rotate2(vec2,new float[]{-vVel[1],-vVel[0]});
            VecFactory.unitize2(vec2);

            vec3[0]=vec2[0]*VEL_SCALE;
            vec3[1]=-9.8f * VEL_SCALE;
            vec3[2]=vec2[1]*VEL_SCALE;

            role.setVel(vec3);
        }
        public void setRoleCam(float[] vCam){
            VecFactory.rotate3y(role.getCam(),vCam[0]*CAM_SCALE);
            VecFactory.rotate3y(role.getCamh(),vCam[0]*CAM_SCALE);
            //纵向移动
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

            wallsManager.draw();
            role.draw();

            MatrixState.popMatrix();
        }

        private void drawHUD(){
            MatrixState.pushMatrix();
            MatrixState.setProjectOrtho(-ratio, ratio,-1f,1f,1,10);
            MatrixState.setCamera(0, 0, 0, 0, 0,-1, 0, 1, 0);
            MatrixState.copyMVMatrix();
            GLES20.glEnable(GLES20.GL_BLEND);
            GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

            moveController.draw();

            MatrixState.popMatrix();
        }

        private void gameLogic(){
            wallsManager.calcCollision(role.getPos(),role.getVel());
            role.runLogic();
        }
    }
}
