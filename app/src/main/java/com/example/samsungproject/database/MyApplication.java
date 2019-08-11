package com.example.samsungproject.database;

import android.app.Application;

/*
* Класс, с помощью которого создается singleton AppDatabase.
* Наследуется от Application для того, singleton инициализировался сразу при запуске приложения
* Следует помнить, что для этого требуется обозначение этого (MyApplication) класса в манифесте.
* */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppDatabase.setInstance(this);
    }
}
