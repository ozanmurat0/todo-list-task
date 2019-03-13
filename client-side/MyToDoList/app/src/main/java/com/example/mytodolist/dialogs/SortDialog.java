package com.example.mytodolist.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.example.mytodolist.R;
import com.example.mytodolist.TodoDetailActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SortDialog extends Dialog {

    private Context mContext;

    public SortDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_sort);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btnSortCreatedAtAsc)
    public void onSortCreatedAtAsc(){
        ((TodoDetailActivity)mContext).sortByCreatedAt(1);
        dismiss();
    }

    @OnClick(R.id.btnSortCreatedAtDesc)
    public void btnSortCreatedAtDesc(){
        ((TodoDetailActivity)mContext).sortByCreatedAt(-1);
        dismiss();
    }

    @OnClick(R.id.btnSortDeadLineAsc)
    public void onSortDeadLineAsc(){
        ((TodoDetailActivity)mContext).sortByDeadline(1);
        dismiss();
    }

    @OnClick(R.id.btnSortDeadLineDesc)
    public void onSortDeadLineDesc(){
        ((TodoDetailActivity)mContext).sortByDeadline(-1);
        dismiss();
    }

    @OnClick(R.id.btnSortNameAsc)
    public void onSortNameAsc(){
        ((TodoDetailActivity)mContext).sortByName(1);
        dismiss();
    }

    @OnClick(R.id.btnSortNameDesc)
    public void onSortNameDesc(){
        ((TodoDetailActivity)mContext).sortByName(-1);
        dismiss();
    }

    @OnClick(R.id.btnSortStatus)
    public void onSortStatus(){
        ((TodoDetailActivity)mContext).sortByStatus();
        dismiss();
    }



}
