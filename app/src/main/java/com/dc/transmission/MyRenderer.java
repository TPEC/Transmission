package com.dc.transmission;

import android.opengl.GLSurfaceView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;
import com.threed.jpct.util.MemoryHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by XIeQian on 2017/3/7.
 */

public class MyRenderer implements GLSurfaceView.Renderer {
    private GameDatabase gd=GameDatabase.getInstance();
    private FrameBuffer fb=null;
    private World world=null;
    private Camera camera=null;
    private Light[] lights=null;

    private RGBColor backColor=new RGBColor(0,0,0);

    private long time=System.currentTimeMillis();
    private int fps=0;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if(fb!=null){
            fb.dispose();
        }
        fb=new FrameBuffer(gl,width,height);

        if(MainActivity.master==null){
            initWorld();
            MemoryHelper.compact();
            if(MainActivity.master==null){
                MainActivity.master=gd.getMainActivity();
            }
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //logic

        fb.clear(backColor);
        world.renderScene(fb);
        world.draw(fb);
        fb.display();
        if(System.currentTimeMillis()-time>=1000){
            System.out.println(fps);
            fps=0;
            time= System.currentTimeMillis();
        }
        fps++;
    }

    private void initWorld() {
        world = new World();
        world.setAmbientLight(127, 127, 127);
        lights=null;
        camera=world.getCamera();
        camera.setPosition(new SimpleVector(0,0,0));
        camera.lookAt(new SimpleVector(0,0,0));
    }
}
