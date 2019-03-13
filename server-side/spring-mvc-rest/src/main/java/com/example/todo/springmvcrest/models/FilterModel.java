package com.example.todo.springmvcrest.models;

import java.util.List;

public class FilterModel {

    private String nameQuery;
    private List<Integer> stateQuery;


    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public List<Integer> getStateQuery() {
        return stateQuery;
    }

    public void setStateQuery(List<Integer> stateQuery) {
        this.stateQuery = stateQuery;
    }
}
