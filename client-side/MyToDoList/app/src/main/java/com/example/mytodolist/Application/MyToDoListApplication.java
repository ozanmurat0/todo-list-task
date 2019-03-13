package com.example.mytodolist.Application;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mytodolist.service.ServiceConnector;

public class MyToDoListApplication extends Application {

    public static final String KEY_STORAGE = "my_todo_app";
    SharedPreferences mSharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        mSharedPreferences = getSharedPreferences(KEY_STORAGE, Context.MODE_PRIVATE);
        initServices();

    }

    private void initServices(){
        ServiceConnector.getInstance().init(this);
    }
}
