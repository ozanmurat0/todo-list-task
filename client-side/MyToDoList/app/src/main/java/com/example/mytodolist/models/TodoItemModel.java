package com.example.mytodolist.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.example.mytodolist.modules.GSONManager;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TodoItemModel implements Parcelable {

    private int id;
    private String name;
    private int state;
    private String description;
    private int listId;
    private String deadline;
    private String createdAt;


    public TodoItemModel(String name, int state, String description, int listId, String deadline) {
        this.name = name;
        this.state = state;
        this.description = description;
        this.listId = listId;
        this.deadline = deadline;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getListId() {
        return listId;
    }

    public void setListId(int listId) {
        this.listId = listId;
    }

    public TodoItemModel() {
    }

    public static List<TodoItemModel> todoItemsFromJSON(final @Nullable JsonArray jsonArray) {
        final List<TodoItemModel> todoItemModels = new ArrayList<>();
        if(jsonArray != null && !jsonArray.isJsonNull()) {
            final Type type = new TypeToken<ArrayList<TodoItemModel>>(){}.getType();
            final List<TodoItemModel> c = GSONManager.getInstance().model(jsonArray, type);
            todoItemModels.addAll(c);
        }
        return todoItemModels;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeInt(this.state);
        dest.writeString(this.description);
        dest.writeInt(this.listId);
        dest.writeString(this.deadline);
    }

    protected TodoItemModel(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.state = in.readInt();
        this.description = in.readString();
        this.listId = in.readInt();
        this.deadline = in.readString();
    }

    public static final Creator<TodoItemModel> CREATOR = new Creator<TodoItemModel>() {
        @Override
        public TodoItemModel createFromParcel(Parcel source) {
            return new TodoItemModel(source);
        }

        @Override
        public TodoItemModel[] newArray(int size) {
            return new TodoItemModel[size];
        }
    };
}
