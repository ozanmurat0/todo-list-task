package com.example.mytodolist.modules;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.lang.reflect.Type;

public class GSONManager {
    private static final GSONManager mInstance = new GSONManager();

    private final Gson mGson;

    private GSONManager() {
        mGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    public static GSONManager getInstance() {
        return mInstance;
    }

    public <T> T model(final @NonNull JsonElement json, final @NonNull Class<T> clz) {
        return mGson.fromJson(json, clz);
    }

    public <T> T model(final @NonNull String json, final @NonNull Class<T> clz) {
        return mGson.fromJson(json, clz);
    }

    public <T> T model(final @NonNull JsonElement json, final @NonNull Type type) {
        return mGson.fromJson(json, type);
    }

    public <T> T model(final @NonNull String json, final @NonNull Type type) {
        return mGson.fromJson(json, type);
    }

    public String json(final @NonNull Object object) {
        return mGson.toJson(object);
    }
}
