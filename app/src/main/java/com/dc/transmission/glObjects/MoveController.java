package com.dc.transmission.glObjects;

import android.graphics.RectF;
import android.view.MotionEvent;

/**
 * Created by Irene on 2017/4/8.
 */

public class MoveController implements TGLObjects {
    private float[] moveVector = new float[2];
    private float mVecLength;
    private int moveID=-1;

    private RectF rectDst;
    private float rectDstMX;
    private float rectDstMY;

    public MoveController(RectF rectDst){
        this.rectDst=rectDst;
        rectDstMX=rectDst.centerX();
        rectDstMY=rectDst.centerY();
    }

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
                    if (rectDst.contains(x,y)) {
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
            if (x== rectDstMX && y== rectDstMY) {
                moveVector[0]=0;
                moveVector[1]=0;
            }
            else{
                moveVector[0] = x - rectDstMX;
                moveVector[1] = y - rectDstMY;
                mVecLength = (float) Math.sqrt(moveVector[0]*moveVector[0] + moveVector[1]*moveVector[1]);
                moveVector[0] = moveVector[0] / mVecLength;
                moveVector[1] = moveVector[1] / mVecLength;
            }
            return true;
        }
        return false;
    }
}
