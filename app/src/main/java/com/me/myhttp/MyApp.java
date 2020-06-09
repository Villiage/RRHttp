package com.me.myhttp;

import android.app.Application;


public class MyApp extends Application {


    static MyApp instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;


    }


    public static MyApp getInstance() {
        return instance;
    }


}
