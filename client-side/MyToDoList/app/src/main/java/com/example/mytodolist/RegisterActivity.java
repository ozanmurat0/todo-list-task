package com.example.mytodolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mytodolist.models.UserModel;
import com.example.mytodolist.modules.AppUserManager;
import com.example.mytodolist.modules.GSONManager;
import com.example.mytodolist.service.ServiceConnector;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity{


    @BindView(R.id.etxtEmail)
    MaterialEditText txtEmail;

    @BindView(R.id.txtPassword)
    MaterialEditText txtPassword;

    @BindView(R.id.txtConfirmPassword)
    MaterialEditText txtConfirmPassword;

    @BindView(R.id.txtName)
    MaterialEditText txtName;

    @BindView(R.id.txtSurname)
    MaterialEditText txtSurname;


    @Override
    public int getLayoutResource() {
        return R.layout.activity_register;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    private boolean formValidation() {
        if (txtName.getText().toString().length() == 0 || txtEmail.getText().toString().length() == 0 || txtPassword.toString().length() == 0
                || txtConfirmPassword.toString().length() == 0 ) {
            toast(getString(R.string.form_valid_message));
            return false;
        } else if (!txtPassword.getText().toString().equals(txtConfirmPassword.getText().toString())) {
            toast(getString(R.string.passwords_same));
            return false;
        }
        return true;
    }


    private void userRegister(){

        if(formValidation()){
            showProgress();
            UserModel userModel = new UserModel();
            userModel.setEmail(txtEmail.getText().toString());
            userModel.setName(txtName.getText().toString());
            userModel.setSurname(txtSurname.getText().toString());
            userModel.setPassword(txtPassword.getText().toString());
            ServiceConnector.getInstance().service().createUser(userModel).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideProgress();
                    if(response.body() != null){
                        if(response.body().get("success").getAsBoolean()){
                            final UserModel userModel = GSONManager.getInstance().model(response.body().get("data").getAsJsonArray().get(0), UserModel.class);
                            AppUserManager.getInstance().setUser(userModel);
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
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

    @OnClick(R.id.btnRegister)
    public void onRegister(){
        userRegister();
    }

    @OnClick(R.id.btnBack)
    public void onBack(){
        finish();
    }

}
