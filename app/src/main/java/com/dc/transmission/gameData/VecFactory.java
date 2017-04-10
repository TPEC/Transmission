package com.dc.transmission.gameData;

/**
 * Created by XIeQian on 2017/4/10.
 */

public class VecFactory {
    public static float[] crossProduct(float[] va,float[] vb){
        float[] vc=new float[3];
        vc[0]=va[1]*vb[2]-va[2]*vb[1];
        vc[1]=va[2]*vb[0]-va[0]*vb[2];
        vc[2]=va[0]*vb[1]-va[1]*vb[0];
        return vc;
    }

    public static void unitize3(float[] vec){
        float s=Math.abs(vec[0]*vec[0]+vec[1]*vec[1]+vec[2]*vec[2]);
        if(s>0){
            s=1/s;
            multiply3(vec,s);
        }
    }

    public static void unitize2(float[] vec){
        float s=Math.abs(vec[0]*vec[0]+vec[1]*vec[1]);
        if(s>0){
            s=1/s;
            multiply2(vec,s);
        }
    }

    public static void multiply3(float[] vec, float k){
        vec[0]*=k;
        vec[1]*=k;
        vec[2]*=k;
    }

    public static void multiply2(float[] vec, float k){
        vec[0]*=k;
        vec[1]*=k;
    }
}
