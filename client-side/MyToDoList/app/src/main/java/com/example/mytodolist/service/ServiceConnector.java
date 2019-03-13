package com.example.mytodolist.service;

import android.content.Context;
import android.support.annotation.NonNull;


public class ServiceConnector {
    private static final ServiceConnector mInstance = new ServiceConnector();

    private MyTodoService mService;

    private ServiceConnector() {}

    public void init(final @NonNull Context context) {
        mService = ServiceGenerator.createService(context, MyTodoService.class);
    }

    public static ServiceConnector getInstance() {
        return mInstance;
    }

    public MyTodoService service() {
        return mService;
    }

}
