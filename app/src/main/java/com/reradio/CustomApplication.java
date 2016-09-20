package com.reradio;

import android.app.Application;

import com.utils.DebugLogger;

public class CustomApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initLogging();
    }

    private void initLogging(){
        if (BuildConfig.DEBUG) {
            DebugLogger.enableLogging();
        }
    }

}

