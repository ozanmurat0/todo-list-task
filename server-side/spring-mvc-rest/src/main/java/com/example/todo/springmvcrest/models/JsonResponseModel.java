package com.example.todo.springmvcrest.models;

import java.util.ArrayList;
import java.util.List;

public class JsonResponseModel {

    private boolean success = false;
    private String msg;
    private List<Object> data = new ArrayList<Object>();


    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }
}
