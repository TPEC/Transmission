package com.dc.transmission;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;

import com.threed.jpct.Camera;
import com.threed.jpct.FrameBuffer;
import com.threed.jpct.Light;
import com.threed.jpct.Object3D;
import com.threed.jpct.RGBColor;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.Texture;
import com.threed.jpct.TextureManager;
import com.threed.jpct.World;
import com.threed.jpct.util.BitmapHelper;
import com.threed.jpct.util.ExtendedPrimitives;
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
    public SimpleVector pos=null;
    private Light[] lights=null;
    private Object3D outerBox=null;
    private TextureManager textureManager=null;
    private Resources resources;

    private RGBColor backColor=new RGBColor(0,0,0);

    private long time=System.currentTimeMillis();
    private int fps=0;

    public MyRenderer(Resources resources){
        this.resources=resources;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if(fb!=null){
            fb.dispose();
        }
        fb=new FrameBuffer(width,height);
        textureManager=TextureManager.getInstance();
        if(MainActivity.master==null){
            //initialize the world
            Bitmap bmp=BitmapFactory.decodeResource(resources,R.mipmap.ic_launcher);
            Texture texture=new Texture(BitmapHelper.rescale(bmp, 64, 64));
            textureManager.addTexture("outer",texture);
            initWorld(null);

            MemoryHelper.compact();
            if(MainActivity.master==null){
                MainActivity.master=gd.getMainActivity();
            }
        }
    }

    public void initWorld(Level level){
        world = new World();
        world.setAmbientLight(127, 127, 127);
        camera=world.getCamera();

        outerBox= ExtendedPrimitives.createBox(new SimpleVector(10,20,30));
        outerBox.calcTextureWrapSpherical();
        outerBox.setTexture("outer");
        outerBox.strip();

        outerBox.build();
        world.addObject(outerBox);
        camera.setPosition(50,50,0);
        camera.lookAt(outerBox.getCenter());
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        //touch event process
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
}
