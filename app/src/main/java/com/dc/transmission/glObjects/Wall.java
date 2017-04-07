package com.dc.transmission.glObjects;

/**
 * Created by helynn on 2017/4/7.
 */

public class Wall implements TGLObjects {
    @Override
    public void draw(int texId) {

    }
    private float[] nVec;
    private float[] md;
    private float width,height;

    public Wall(float width,float height){
        nVec=new float[3];
        md=new float[3];
        this.width=width;
        this.height=height;

    }
}
