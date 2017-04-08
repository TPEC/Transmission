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
        String[] strData = sData.split(",");
        float[] nVecData;
        float[] mdData;
        for (int i = 0;i <= 2;i++){
            nVecData[i]=Float.parseFloat(strData[i]);
        }
        for (int i = 3; i <= 5; i++) {
            mdData[i-3] = Float.parseFloat(strData[i]);
        }
        setnVec(nVecData);
        setMd(mdData);
        width = Float.parseFloat(strData[6]);
        height = Float.parseFloat(strData[7]);

    }

    public boolean checkCollision(float[] pos,float[] vel){//检测碰撞，输入位置向量、速度向量，返回是否碰撞，并修改速度向量，使得pos+vel刚好贴墙。墙的厚度为Constant.WALL_WIDTH
        //墙面的范围：x:(md[0]-height/2,md[0]+height/2),y:(md[1]-width/2, md[1]+width/2),z: md[2]+Constant.WALL_WIDTH
        float x1=md[0]-height/2;
        float x2=md[0]+height/2;
        float y1=md[1]-width/2;
        float y2=md[1]+width/2;

        //设置墙面中心点
        float [] mid=new float[3];
        mid[0]=md[0];
        mid[1]=md[1];
        mid[2]=md[2]+ Constant.WALL_WIDTH;

        if(vel[0]*nVec[0]+vel[1]*nVec[1]+vel[2]*nVec[2]==0)
            return false;//平面与向量平行，不会相交,不会碰撞
        else{
            //计算射线与平面的交点
            //nVec[0]*(vel[0]*t+pos[0]-mid[0])+nVec[1]*(vel[1]*t+pos[1]-mid[1])+nVec[2]*(vel[2]*t+pos[2]-mid[2])=0 解出t得到交点坐标
            float t;
            t=(nVec[0]*(mid[0]-pos[0])+nVec[1]*(mid[1]-pos[1])+nVec[2]*(mid[2]-pos[2]))/(nVec[0]*vel[0]+nVec[1]*vel[1]+nVec[2]*vel[2]);
            float [] meet=new float[3]; //meet为射线与平面交点坐标
            meet[0]=vel[0]*t+pos[0];
            meet[1]=vel[1]*t+pos[1];
            meet[2]=vel[2]*t+pos[2];

            //判断交点是否在墙面范围内
            if(meet[0]>=x1 && meet[0]<=x2 && meet[1]>=y1 && meet[1]<=y2 && meet[2]==mid[2]){
                //修改速度向量
                vel[0]=meet[0]-pos[0];
                vel[1]=meet[1]-pos[1];
                vel[2]=meet[2]-pos[2];
                return true;
            }
            else
                return false;
        }
    }

    public float getDistance(float[] point,float[] direct){//返回从point出发的沿direct方向的射线与该墙的距离，若射线与墙无交点则返回0.
        if(checkCollision(point,direct)==false)
            return 0;
        else{   //墙面方程：nVec[0]*(x-x0)+nVec[1]*(y-y0)+nVec[2]*(z-z0)=0
            //设置墙面中心点
            float [] mid=new float[3];
            mid[0]=md[0];
            mid[1]=md[1];
            mid[2]=md[2]+ Constant.WALL_WIDTH;
            double d=Math.abs(nVec[0]*(point[0]-mid[0])+nVec[1]*(point[1]-mid[1])+nVec[2]*(point[2]-mid[2]))/Math.sqrt(Math.pow(nVec[0],2)+Math.pow(nVec[1],2)+Math.pow(nVec[2],2));
            float distance=(float)d;
            return distance;
        }
    }

    @Override
    public void draw(int texId) {

    }
}
