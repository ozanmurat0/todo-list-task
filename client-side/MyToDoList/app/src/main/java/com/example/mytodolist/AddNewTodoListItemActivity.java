package com.example.mytodolist;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.example.mytodolist.adapters.DependencyAdapter;
import com.example.mytodolist.models.TodoItemModel;
import com.example.mytodolist.modules.GSONManager;
import com.example.mytodolist.service.ServiceConnector;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTodoListItemActivity extends BaseActivity {

    DatePickerDialog mDatePickerDeadlineDialog;
    public static String LIST_ID = "list_id";
    private int listId;
    private List<TodoItemModel> mTodoItemModels;
    private List<Integer> dependencies = new ArrayList<Integer>();

    @BindView(R.id.rView)
    RecyclerView rView;

    @BindView(R.id.txtName)
    MaterialEditText txtName;

    @BindView(R.id.layoutDeadline)
    LinearLayout layoutDeadline;

    @BindView(R.id.txtDeadline)
    MaterialEditText txtDeadline;

    @BindView(R.id.txtDescription)
    MaterialEditText txtDescription;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_todo_item;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listId = Integer.parseInt(getIntent().getStringExtra(LIST_ID));
        updateUI();
        Calendar now = Calendar.getInstance();
        mDatePickerDeadlineDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        monthOfYear = monthOfYear + 1;
                        String month = String.valueOf(monthOfYear);
                        if(month.length() == 1){
                            month = "0"+month;
                        }
                        txtDeadline.setText(year + "-" + month + "-" + dayOfMonth);
                    }

                },
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        mDatePickerDeadlineDialog.getDatePicker().setMinDate(now.getTimeInMillis());
    }


    private void updateUI(){
        getTodoListItems();
    }

    private void setAdapter(){
        rView.setLayoutManager(new LinearLayoutManager(this));
        rView.setAdapter(new DependencyAdapter(this, mTodoItemModels));
    }

    public void addDependencies(int value){
        if(dependencies.contains(value)){
            for(int i=0; i< dependencies.size() ; i++){
                if(dependencies.get(i) == value){
                    dependencies.remove(i);
                }
            }
        }else{
            dependencies.add(value);
        }
    }

    private void getTodoListItems(){
        ServiceConnector.getInstance().service().getTodoListItemsNotCompleted(listId).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.body() != null){
                    final List<TodoItemModel> todoItemModels = TodoItemModel.todoItemsFromJSON(response.body().get("data").getAsJsonArray());
                    if(todoItemModels != null){
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

    private boolean formValidation(){
        if (txtName.getText().toString().length() == 0 && txtDeadline.getText().toString().length() == 0 && txtDescription.getText().toString().length() == 0) {
            return false;
        }
        return true;
    }

    private void createItem(){
        if(formValidation()){
            showProgress();
            TodoItemModel todoItemModel = new TodoItemModel(
                    txtName.getText().toString(),
                    1,
                    txtDescription.getText().toString(),
                    listId,
                    txtDeadline.getText().toString()
            );
            ServiceConnector.getInstance().service().createTodoListItem(todoItemModel).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    if(response.body() != null){
                        if(response.body().get("success").getAsBoolean()){
                            toast(response.body().get("msg").getAsString());
                            final TodoItemModel todoItemModel1 = GSONManager.getInstance().model(response.body().get("data").getAsJsonArray().get(0), TodoItemModel.class);
                            addDependencies(todoItemModel1);
                        }


                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    hideProgress();
                }
            });
        }else{
            toast("Please fill the required fields.");
        }
    }


    private void addDependencies(TodoItemModel todoItemModel){
        ServiceConnector.getInstance().service().addDependencies(dependencies,todoItemModel.getId()).enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                hideProgress();
                finish();
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                hideProgress();
            }
        });
    }

    @OnClick(R.id.btnCreate)
    public void onItemCreate(){
        createItem();
    }


    @OnClick(R.id.btnBack)
    public void onBack(){
        finish();
    }

   /* @OnClick(R.id.layoutDeadline)
    public void showDateDialog(){
        mDatePickerDeadlineDialog.show();
    }*/

    @OnClick(R.id.txtDeadline)
    public void showTxtDateDialog(){
        mDatePickerDeadlineDialog.show();
    }

}
