package com.dc.transmission;

import android.content.res.Resources;

/**
 * Created by XIeQian on 2017/3/7.
 */

public class GameDatabase {
    private static final GameDatabase ourInstance = new GameDatabase();

    public static GameDatabase getInstance() {
        return ourInstance;
    }

    private GameDatabase() {
    }

    private MainActivity mainActivity=null;
    private MyRenderer renderer=null;
    private Resources resources;

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MyRenderer getRenderer() {
        if(renderer==null)
            renderer=new MyRenderer(resources);
        return renderer;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
