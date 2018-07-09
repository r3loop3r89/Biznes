package com.shra1.biznes;

import android.app.Application;
import android.content.Context;

public class App extends Application {
    private static App INSTANCE;

    public static Context getApp() {
        return INSTANCE;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
