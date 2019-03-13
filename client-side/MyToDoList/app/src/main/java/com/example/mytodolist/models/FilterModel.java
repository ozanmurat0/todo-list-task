package com.example.mytodolist.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class FilterModel implements Parcelable {

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

    public FilterModel() {
    }


    public FilterModel(String nameQuery, List<Integer> stateQuery) {
        this.nameQuery = nameQuery;
        this.stateQuery = stateQuery;
    }

    protected FilterModel(Parcel in) {
        this.nameQuery = in.readString();
        this.stateQuery = new ArrayList<Integer>();
        in.readList(this.stateQuery, Integer.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nameQuery);
        dest.writeList(this.stateQuery);
    }

    public static final Parcelable.Creator<FilterModel> CREATOR = new Parcelable.Creator<FilterModel>() {
        @Override
        public FilterModel createFromParcel(Parcel source) {
            return new FilterModel(source);
        }

        @Override
        public FilterModel[] newArray(int size) {
            return new FilterModel[size];
        }
    };
}
