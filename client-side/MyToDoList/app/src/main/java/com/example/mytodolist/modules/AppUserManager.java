package com.example.mytodolist.modules;

import android.support.annotation.Nullable;

import com.example.mytodolist.models.UserModel;

public class AppUserManager {
    private static final AppUserManager mInstance = new AppUserManager();

    private UserModel mUser;

    private AppUserManager() {}

    public static AppUserManager getInstance() {
        return mInstance;
    }

    public boolean isLogged() {
        return mUser != null;
    }


    public void setUser(final @Nullable UserModel user) {
        this.mUser = user;
    }

    @Nullable
    public UserModel getUser() {
        return mUser;
    }

    public void clear() {
        this.mUser = null;
    }
}
