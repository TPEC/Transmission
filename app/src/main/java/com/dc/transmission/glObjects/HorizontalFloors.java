package com.dc.transmission.glObjects;

/**
 * Created by XIeQian on 2017/4/3.
 */

public class HorizontalFloors implements TGLObjects {
    private PhysFloors[] floors;

    public HorizontalFloors(){

    }

    public void calcPhysics(float[] pos, float[] vel){
        for(int i=0;i<floors.length;i++){
            if(pos[0]>=floors[i].left && pos[0]<floors[i].right && pos[2]>=floors[i].top && pos[2]<floors[i].bottom){

            }
        }
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
