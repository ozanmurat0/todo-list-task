package com.example.mytodolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.mytodolist.models.UserModel;
import com.example.mytodolist.modules.AppUserManager;
import com.example.mytodolist.modules.GSONManager;
import com.example.mytodolist.service.ServiceConnector;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rengwuxian.materialedittext.MaterialEditText;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.etxtEmail)
    MaterialEditText etxtEmail;

    @BindView(R.id.etxtPassword)
    MaterialEditText etxtPassword;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isLogged();
    }


    private boolean formValidation() {
        if (etxtEmail.getText().toString().length() == 0 || etxtPassword.getText().toString().length() == 0) {
            return false;
        }
        return true;
    }


    private void isLogged() {
        Gson gson = new Gson();
        String json = mSharedPreferences.getString("user", "");
        if (!json.equalsIgnoreCase("")) {
            UserModel user = gson.fromJson(json, UserModel.class);
            if (user != null) {
                AppUserManager.getInstance().setUser(user);
                onStartIntro();
            }
        }
    }

    private void onStartIntro() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @OnClick(R.id.btnLogin)
    public void onLogin() {
        if (formValidation()) {
            showProgress();
            ServiceConnector.getInstance().service().login(etxtEmail.getText().toString(), etxtPassword.getText().toString()).enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    hideProgress();
                    if(response.body() != null){
                        if(response.body() != null){
                            if(response.body().get("success").getAsBoolean()){
                                final UserModel userModel = GSONManager.getInstance().model(response.body().get("data").getAsJsonArray().get(0), UserModel.class);
                                AppUserManager.getInstance().setUser(userModel);
                                completeLogin(userModel);
                            }else{
                                toast(response.body().get("msg").getAsString());
                            }
                        }
                    }else{
                        toast("Incorrect email or password!");
                    }
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    hideProgress();
                }
            });
        }else{
            toast("Please fill the required fields");
        }
    }


    private void completeLogin(UserModel user) {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(user);
        editor.putString("user",json);
        editor.apply();
        startActivity(new Intent(this, MainActivity.class));
    }

    @OnClick(R.id.btnRegister)
    public void onClickRegister() {
        Intent intentRegister = new Intent(this, RegisterActivity.class);
        startActivity(intentRegister);
    }


}
