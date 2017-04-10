package com.dc.transmission.glObjects;

import com.dc.transmission.gameData.TexFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XIeQian on 2017/4/3.
 */

public class WallsManager implements TGLObjects {
    private List<Wall> walls;
    private int[] textureID;

    public WallsManager(){
        walls=new ArrayList<>();
    }

    public void clearWalls(){
        walls.clear();
    }

    public void addWall(){
        Wall wall=new Wall(TexFactory.glSv);
        wall.setTextureID(0);
        wall.set
        walls.add(wall);
    }

    public void calcCollision(float[] pos, float[] vel){
        for(Wall w:walls){
            w.checkCollision(pos,vel);
        }
    }

    @Override
    public void draw() {
        for(Wall w:walls){
            w.draw();
        }
    }

    public void initTexture(){
        textureID =new int[1];
        textureID[0]=TexFactory.getNewTexture(0,0);
    }
}
