package com.dc.transmission.glObjects;

import android.view.MotionEvent;

/**
 * Created by Irene on 2017/4/9.
 */

public class PortalController implements TGLObjects {
    private int portalID=-1;
    private boolean clicked=false;

    @Override
    public void draw(int texId) {

    }

    public boolean onTouchEvent (MotionEvent event){
        int pointerCount = event.getPointerCount();
        int action = event.getActionMasked();
        float x = 0, y = 0;
        if (action==MotionEvent.ACTION_DOWN || action==MotionEvent.ACTION_POINTER_DOWN) {
            if (portalID < 0) {
                for (int i = 0; i < pointerCount; i++) {
                    x = event.getX(i);
                    y = event.getY(i);
                    if (x>=960 && x<=1080 && y>=540 && y<=660) {
                        portalID = event.getPointerId(i);
                        break;
                    }
                }
            }
        }else if (action==MotionEvent.ACTION_UP || action==MotionEvent.ACTION_POINTER_UP) {
            if (event.getPointerId(event.getActionIndex()) == portalID) {
                clicked=true;
                portalID = -1;
                return true;
            }
        }
        if (portalID>=0){
            for (int i = 0; i < pointerCount; i++) {
                if (event.getPointerId(i)==portalID) {
                    x = event.getX(i);
                    y = event.getY(i);
                    break;
                }
            }
            ////////////////////////////////////////////////
            return true;
        }
        return false;
    }

    public boolean getClicked(){
        if(clicked){
            clicked=false;
            return true;
        }
        return false;
    }
}
