package com.dc.transmission.glObjects;

/**
 * Created by XIeQian on 2017/4/3.
 */

public class WallsManager implements TGLObjects {
    private Wall[] walls;

    public WallsManager(){

    }

    public void initWalls(int wallCount){
        walls=new Wall[wallCount];
    }

    public void calcCollision(float[] pos, float[] vel){
        for(int i=0;i<walls.length;i++){
            walls[i].checkCollision(pos,vel);
        }
    }

    @Override
    public void draw(int texId) {
        for(int i=0;i<walls.length;i++){
            walls[i].draw(texId);
        }
    }
}
