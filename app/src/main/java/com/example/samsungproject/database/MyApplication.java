package com.example.samsungproject.database;

import android.app.Application;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.setInstance(this);
    }
}
