package com.dc.transmission.glObjects;

/**
 * Created by XIeQian on 2017/4/3.
 */

public class Role implements TGLObjects {
    private float[] pos;
    private float[] vel;

    public Role(){
        pos=new float[3];
        vel=new float[3];

    }

    @Override
    public void draw(int texId) {

    }
}
