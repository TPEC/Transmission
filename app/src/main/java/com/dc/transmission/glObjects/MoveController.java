package com.dc.transmission.glObjects;

import android.view.MotionEvent;

/**
 * Created by Irene on 2017/4/8.
 */

public class MoveController implements TGLObjects {
    private float[] moveVector = new float[2];
    private float mvecLength;
    private int moveID=-1;
    private static final int MOVE_X=200;
    private static final int MOVE_Y=540;

    @Override
    public void draw(int texId) {

    }

    public float[] getMoveVector(){
        return moveVector;
    }

    public boolean getClickDown(){
        return (moveID!=-1);
    }

    public boolean onTouchEvent(MotionEvent event){
        int pointerCount = event.getPointerCount();
        int action = event.getActionMasked();
        float x = 0, y = 0;
        if (action==MotionEvent.ACTION_DOWN || action==MotionEvent.ACTION_POINTER_DOWN) {
            if (moveID < 0) {
                for (int i = 0; i < pointerCount; i++) {
                    x = event.getX(i);
                    y = event.getY(i);
                    if (x>=80 && x<=320 && y>=420 && y<= 660) {
                        moveID = event.getPointerId(i);
                        break;
                    }
                }
            }
        }else if (action==MotionEvent.ACTION_UP || action==MotionEvent.ACTION_POINTER_UP) {
            if (event.getPointerId(event.getActionIndex()) == moveID) {
                moveID = -1;
                return true;
            }
        }
        if (moveID>=0){
            for (int i = 0; i < pointerCount; i++) {
                if (event.getPointerId(i)==moveID) {
                    x = event.getX(i);
                    y = event.getY(i);
                    break;
                }
            }
            if (x==MOVE_X && y==MOVE_Y) {
                moveVector[0]=0;
                moveVector[1]=0;
            }
            else{
                moveVector[0] = x - MOVE_X;
                moveVector[1] = y - MOVE_Y;
                mvecLength = (float) Math.sqrt(Math.pow(moveVector[0], 2) + Math.pow(moveVector[1], 2));
                moveVector[0] = moveVector[0] / mvecLength;
                moveVector[1] = moveVector[1] / mvecLength;
            }
            return true;
        }
        return false;
    }
}
