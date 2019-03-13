package com.example.mytodolist.models;

import android.support.annotation.Nullable;

import com.example.mytodolist.modules.GSONManager;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TodoModel {

    private int id;
    private String name;
    private int user_id;
    private String createdAt;

    public TodoModel(String name, int user_id) {
        this.name = name;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public static List<TodoModel> todoListFromJSON(final @Nullable JsonArray jsonArray) {
        final List<TodoModel> todoModels = new ArrayList<>();
        if(jsonArray != null && !jsonArray.isJsonNull()) {
            final Type type = new TypeToken<ArrayList<TodoModel>>(){}.getType();
            final List<TodoModel> c = GSONManager.getInstance().model(jsonArray, type);
            todoModels.addAll(c);
        }
        return todoModels;
    }
}
