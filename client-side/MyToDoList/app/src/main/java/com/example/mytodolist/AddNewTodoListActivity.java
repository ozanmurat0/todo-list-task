package com.example.mytodolist;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mytodolist.models.TodoModel;
import com.example.mytodolist.models.UserModel;
import com.example.mytodolist.modules.AppUserManager;
import com.example.mytodolist.service.ServiceConnector;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNewTodoListActivity extends BaseActivity {

    private UserModel mUser;

    @BindView(R.id.txtName)
    MaterialEditText txtName;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_add_todo_list;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = AppUserManager.getInstance().getUser();
    }

    @OnClick(R.id.btnBack)
    public void onBack(){
        finish();
    }

    @OnClick(R.id.btnCreate)
    public void onTodoCreate(){
        if(formValidation()){
            TodoModel todoModel = new TodoModel(txtName.getText().toString(), mUser.getId());
            showProgress();
            ServiceConnector.getInstance().service().createTodoList(todoModel).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideProgress();
                    if(response.body() != null){
                        if(response.body().get("success").getAsBoolean()){
                            toast(response.body().get("msg").getAsString());
                            finish();
                        }else{
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

    }

    private boolean formValidation() {
        if (txtName.getText().toString().length() == 0) {
            return false;
        }
        return true;
    }


}
