package com.dc.transmission;

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

    public void setMainActivity(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public MyRenderer getRenderer() {
        if(renderer==null)
            renderer=new MyRenderer();
        return renderer;
    }

    public MainActivity getMainActivity() {
        return mainActivity;
    }
}
