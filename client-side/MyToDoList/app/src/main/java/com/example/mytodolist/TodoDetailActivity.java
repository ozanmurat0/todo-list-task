package com.example.mytodolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mytodolist.adapters.ToDoItemsAdapter;
import com.example.mytodolist.dialogs.SortDialog;
import com.example.mytodolist.models.FilterModel;
import com.example.mytodolist.models.TodoItemModel;
import com.example.mytodolist.service.ServiceConnector;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TodoDetailActivity extends BaseActivity {

    public static String TODO_ID = "todo_id";
    public static String TODO_NAME = "todo_name";
    private int listId;
    private List<TodoItemModel> mTodoItemModels;
    private ToDoItemsAdapter mAdapter;
    private List<Integer> stateQuery = new ArrayList<Integer>();
    private Display mDisplay;
    private String todoName;

    @BindView(R.id.rView)
    RecyclerView rView;

    @BindView(R.id.txtTitle)
    TextView txtTitle;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.layoutFilter)
    LinearLayout layoutFilter;

    @BindView(R.id.txtFilterName)
    MaterialEditText txtFilterName;

    @BindView(R.id.checkboxExpired)
    CheckBox checkboxExpired;

    @BindView(R.id.checkboxNotCompleted)
    CheckBox checkboxNotCompleted;

    @BindView(R.id.checkboxCompleted)
    CheckBox checkboxCompleted;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_todo_detail;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listId = Integer.parseInt(getIntent().getStringExtra(TODO_ID));
        todoName = getIntent().getStringExtra(TODO_NAME);
        txtTitle.setText(todoName);
        updateUI();
    }

    private void updateUI() {
        getTodoListItems();
        setSwipeRefresh();
        setCheckChangeListeners();
        mDisplay = this.getWindowManager().getDefaultDisplay();
    }


    private void setSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //get to-do list items
                getTodoListItems();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getTodoListItems();
    }

    private void setAdapter() {
        mAdapter = new ToDoItemsAdapter(this, mTodoItemModels);
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(mAdapter);
    }

    public void removeStateQuery(int value){
        if(stateQuery.contains(value)){
            for(int i=0; i< stateQuery.size() ; i++){
                if(stateQuery.get(i) == value){
                    stateQuery.remove(i);
                }
            }
        }
    }

    private void setCheckChangeListeners(){
        checkboxExpired.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    stateQuery.add(0);
                }else{
                    removeStateQuery(0);
                }
            }
        });

        checkboxNotCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    stateQuery.add(1);
                }else{
                    removeStateQuery(1);
                }
            }
        });

        checkboxCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    stateQuery.add(2);
                }else{
                    removeStateQuery(2);
                }
            }
        });

    }

    /* REST API Request Start Here */

    private void getTodoListItems() {
        swipeRefreshLayout.setRefreshing(false);
        ServiceConnector.getInstance().service().getTodoListItems(listId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.body() != null) {
                    final List<TodoItemModel> todoItemModels = TodoItemModel.todoItemsFromJSON(response.body().get("data").getAsJsonArray());
                    if (todoItemModels != null) {
                        mTodoItemModels = todoItemModels;
                        setAdapter();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    public void removeItemFromList(int id, final int position) {
        showProgress();
        ServiceConnector.getInstance().service().deleteTodoListItem(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideProgress();
                if (response.body() != null) {
                    if (response.body().get("success").getAsBoolean()) {
                        toast(response.body().get("msg").getAsString());
                        mAdapter.removeItem(position);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideProgress();
            }
        });
    }


    public void markAsCompleted(int id, final int position) {
        showProgress();
        ServiceConnector.getInstance().service().completeItem(id).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideProgress();
                if (response.body() != null) {
                    if (response.body().get("success").getAsBoolean()) {
                        toast(response.body().get("msg").getAsString());
                        mAdapter.setState(position, 2);
                    } else {
                        toast(response.body().get("msg").getAsString());
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideProgress();
            }
        });
    }

    private void listFilterItems(){
        showProgress();
        if(stateQuery.size() == 0){
            stateQuery.add(0);
            stateQuery.add(1);
            stateQuery.add(2);
        }
        FilterModel filterModel = new FilterModel(txtFilterName.getText().toString(),stateQuery);
        ServiceConnector.getInstance().service().filterListItems(filterModel,listId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideProgress();
                if(response.body() != null){
                    final List<TodoItemModel> todoItemModels = TodoItemModel.todoItemsFromJSON(response.body().get("data").getAsJsonArray());
                    if(todoItemModels != null){
                        //stateQuery.clear();
                        mAdapter.setList(todoItemModels);
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideProgress();
            }
        });
    }

    /* REST API Request End Here */


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
                        removeItemFromList(id, position);

                    }
                })
                .setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }



    /* ASC  1, -1 DESC */
    public void sortByName(final int order) {
        List<TodoItemModel> tempList = mTodoItemModels;
        Collections.sort(tempList, new Comparator<TodoItemModel>() {
            @Override
            public int compare(TodoItemModel o1, TodoItemModel o2) {
                return o1.getName().compareToIgnoreCase(o2.getName()) * order;
            }
        });
        mAdapter.setList(tempList);
    }

    public void sortByStatus(){
        List<TodoItemModel> tempList = mTodoItemModels;
        Collections.sort(tempList, new Comparator<TodoItemModel>() {
            @Override
            public int compare(TodoItemModel o1, TodoItemModel o2) {
                int compare = 0;
                if(o1.getState() > o2.getState()){
                    compare = 1;
                }else if(o1.getState() < o2.getState()){
                    compare = -1;
                }
                return compare * -1;
            }
        });
        mAdapter.setList(tempList);
    }

    public void sortByCreatedAt(final int order) {
        List<TodoItemModel> tempList = mTodoItemModels;
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Collections.sort(tempList, new Comparator<TodoItemModel>() {
            @Override
            public int compare(TodoItemModel o1, TodoItemModel o2) {
                int compare = 0;
                try {
                    compare = formatter.parse(o1.getCreatedAt()).compareTo(formatter.parse(o2.getCreatedAt())) * order;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return compare;
            }
        });
        mAdapter.setList(tempList);
    }

    public void sortByDeadline(final int order){
        List<TodoItemModel> tempList = mTodoItemModels;
        final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Collections.sort(tempList, new Comparator<TodoItemModel>() {
            @Override
            public int compare(TodoItemModel o1, TodoItemModel o2) {
                int compare = 0;
                try {
                    compare = formatter.parse(o1.getDeadline()).compareTo(formatter.parse(o2.getDeadline())) * order;
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return compare;
            }
        });
        mAdapter.setList(tempList);
    }

    public void showFilter() {
        int width = mDisplay.getHeight();
        TranslateAnimation animate = new TranslateAnimation(width, 0, 0, 0);
        animate.setDuration(300);
        animate.setFillAfter(false);
        layoutFilter.startAnimation(animate);
        layoutFilter.setVisibility(View.VISIBLE);
    }


    public void hideFilter() {
        int width = mDisplay.getHeight();
        TranslateAnimation animate = new TranslateAnimation(0, width, 0, 0);
        animate.setDuration(300);
        animate.setFillAfter(false);
        layoutFilter.startAnimation(animate);
        layoutFilter.setVisibility(View.GONE);
    }


    @OnClick(R.id.btnFilter)
    public void onFilter() {
        showFilter();
    }

    @OnClick(R.id.btnSort)
    public void onSort(){
        new SortDialog(this).show();
    }

    @OnClick(R.id.btnAddItem)
    public void onAddItem() {
        Intent intent = new Intent(this, AddNewTodoListItemActivity.class);
        intent.putExtra(AddNewTodoListItemActivity.LIST_ID, String.valueOf(listId));
        startActivity(intent);
    }

    @OnClick(R.id.btnFilterClose)
    public void onFilterClose() {
        hideFilter();
    }

    @OnClick(R.id.btnApply)
    public void onFilterApply() {
        listFilterItems();
    }

    @OnClick(R.id.btnBack)
    public void onBack(){
        finish();
    }
}
