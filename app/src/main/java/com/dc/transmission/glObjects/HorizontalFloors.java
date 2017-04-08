package com.dc.transmission.glObjects;

/**
 * Created by XIeQian on 2017/4/3.
 */

public class HorizontalFloors implements TGLObjects {
    private PhysFloors[] floors;

    public HorizontalFloors(){

    }

    public void calcPhysics(float[] pos, float[] vel){

    }

    @Override
    public void draw(int texId) {

    }

    public class PhysFloors{
        public float left;
        public float top;
        public float right;
        public float bottom;

        public float getWidth(){
            return right-left;
        }

        public float getHeight(){
            return bottom-top;
        }
    }
}
