package com.example.mytodolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mytodolist.adapters.TodoListAdapter;
import com.example.mytodolist.models.TodoModel;
import com.example.mytodolist.models.UserModel;
import com.example.mytodolist.modules.AppUserManager;
import com.example.mytodolist.service.ServiceConnector;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity {


    private UserModel mUser;
    private List<TodoModel> mTodoLists;
    private TodoListAdapter mAdapter;

    @BindView(R.id.rView)
    RecyclerView rView;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = AppUserManager.getInstance().getUser();
        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTodoList();
    }

    private void updateUI() {
        getTodoList();
    }

    private void setAdapter() {
        mAdapter = new TodoListAdapter(this, mTodoLists);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(mAdapter);
    }

    public void removeTodoList(int todoID, final int position) {
        showProgress();
        ServiceConnector.getInstance().service().deleteTodoList(todoID).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideProgress();
                if(response.body() != null){
                    toast(response.body().get("msg").getAsString());
                    mAdapter.removeItem(position);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideProgress();
            }
        });
    }

    private void getTodoList() {
        ServiceConnector.getInstance().service().getTodoLists(mUser.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    final List<TodoModel> todoModels = TodoModel.todoListFromJSON(response.body().get("data").getAsJsonArray());
                    if (todoModels != null) {
                        mTodoLists = todoModels;
                        setAdapter();

                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void removeDialog(final int id, final int position) {
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(Objects.requireNonNull(this), android.R.style.Theme_Material_Light_Dialog_NoActionBar);
        } else {
            builder = new AlertDialog.Builder(Objects.requireNonNull(this));
        }
        builder
                .setMessage("Are you sure to remove item from list.")
                .setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeTodoList(id, position);
                        //logout();

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }


    @OnClick(R.id.btnLogout)
    public void onLogout(){
        clearLocalData();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.btnAddItem)
    public void onAddTodo(){
        Intent intent = new Intent(this, AddNewTodoListActivity.class);
        startActivity(intent);
    }





}
