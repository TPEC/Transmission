package com.dc.transmission.glObjects;

/**
 * Created by XIeQian on 2017/4/3.
 */

public class Role implements TGLObjects {
    private float[] pos;
    private float[] vel;
    private float[] cam,camh;

    public Role(){
        pos=new float[3];
        vel=new float[3];
        cam=new float[3];
        camh=new float[3];
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

    public float[] getCamh(){
        return camh;
    }

    public void runLogic(){
        pos[0]+=vel[0];
        pos[1]+=vel[1];
        pos[2]+=vel[2];
    }

    @Override
    public void draw(int texId) {

    }
}
