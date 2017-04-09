package com.dc.transmission.glObjects;

/**
 * Created by XIeQian on 2017/4/3.
 */

public class Role implements TGLObjects {
    private float[] pos;
    private float[] vel;
    private float[] cam;

    public Role(){
        pos=new float[3];
        vel=new float[3];
        cam=new float[6];
    }

    public void setPos(float[] pos) {
        this.pos = pos;
    }

    public void setVel(float[] vel) {
        this.vel = vel;
    }

    public float[] getPos() {
        return pos;
    }

    public float[] getVel() {
        return vel;
    }

    public float[] getCam() {
        return cam;
    }

    public void runLogic(){
        for(int i=0;i<3;i++)
            pos[i]+=vel[i];
    }

    @Override
    public void draw(int texId) {

    }
}
