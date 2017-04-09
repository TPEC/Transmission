package com.dc.transmission.glObjects;

import android.view.MotionEvent;

/**
 * Created by Irene on 2017/4/9.
 */

public class ViewController implements TGLObjects {
    private int viewID=-1;
    private float lastView[] = new float[2];
    private float thisView[] = new float[2];
    private float[] viewVector = new float[2];
    private int viewCounter=0;

    @Override
    public void draw(int texId) {

    }

    public float[] getMoveVector(){
        return viewVector;
    }

    public boolean getClickDown(){
        return (viewID!=-1);
    }

    private boolean OnTouchEvent (MotionEvent event){
        int pointerCount = event.getPointerCount();
        int action = event.getActionMasked();
        float x = 0, y = 0;
        if (action==MotionEvent.ACTION_DOWN || action==MotionEvent.ACTION_POINTER_DOWN) {
            if (viewID < 0) {
                for (int i = 0; i < pointerCount; i++) {
                    x = event.getX(i);
                    y = event.getY(i);
                    if (!(x>=960 && x<=1080 && y>=540 && y<=660)&&!(x>=80 && x<=320 && y>=420 && y<= 660)) {
                        viewID = event.getPointerId(i);
                        thisView[0]=0;
                        thisView[1]=0;
                        lastView[0]=0;
                        lastView[1]=0;
                        viewVector[0]=0;
                        viewVector[1]=0;
                        break;
                    }
                }
            }
        }else if (action==MotionEvent.ACTION_UP || action==MotionEvent.ACTION_POINTER_UP) {
            if (event.getPointerId(event.getActionIndex()) == viewID) {
                viewID = -1;
                viewCounter=0;
                return true;
            }
        }
        if (viewID>=0){
            viewCounter=viewCounter+1;
            for (int i = 0; i < pointerCount; i++) {
                if (event.getPointerId(i)==viewID) {
                    lastView[0]=thisView[0];
                    lastView[1]=thisView[1];
                    thisView[0] = event.getX(i);
                    thisView[1] = event.getY(i);
                    break;
                }
            }
            if (viewCounter==1){
                viewVector[0]=0;
                viewVector[1]=0;
            }else {
                viewVector[0] = thisView[0] - lastView[0];
                viewVector[1] = thisView[1] - lastView[1];
                return true;
            }
        }
        return false;
    }
}