package com.dc.transmission.glObjects;

/**
 * Created by helynn on 2017/4/7.
 */

public class Wall implements TGLObjects {
    private float[] nVec;
    private float[] md;
    private float width,height;

    public void setnVec(float[] nVec) {
        this.nVec = nVec;
    }

    public void setMd(float[] md) {
        this.md = md;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Wall(){
        nVec=new float[3];
        md=new float[3];
        this.width=0;
        this.height=0;
    }

    public Wall(String sData){//通过sData初始化，sData是以英文逗号分隔的数字，依次对应nVec,md,width,height,如“1,0,0,5,3.3,1.5,2.2,3,5”

    }

    public boolean checkCollision(float[] pos,float[] vel){//检测碰撞，输入位置向量、速度向量，返回是否碰撞，并修改速度向量，使得pos+vel刚好贴墙。墙的厚度为Constant.WALL_WIDTH
        return false;
    }

    public float getDistance(float[] point,float[] direct){//返回从point出发的沿direct方向的射线与该墙的距离，若射线与墙无交点则返回0.
        return 0;
    }

    @Override
    public void draw(int texId) {

    }
}
