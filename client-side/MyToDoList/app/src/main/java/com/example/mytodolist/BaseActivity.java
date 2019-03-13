package com.example.mytodolist;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.view.WindowManager;
import android.widget.Toast;

import com.example.mytodolist.modules.ProgressLoaderManager;


import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity  {

    public abstract int getLayoutResource();

    SharedPreferences mSharedPreferences;
    public static final String KEY_STORAGE = "my_todo_app";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        ButterKnife.bind(this);
        mSharedPreferences = getSharedPreferences(KEY_STORAGE, Context.MODE_PRIVATE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }



    public void clearLocalData() {
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public final void showProgress() {
        ProgressLoaderManager.getInstance().show(this);
    }

    public final void hideProgress() {
        ProgressLoaderManager.getInstance().hide();
    }

    public final void toast(final @NonNull String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public boolean isMainActivity() {
        return getClass().equals(MainActivity.class);
    }

    public boolean isLoginActivity(){
        return getClass().equals(LoginActivity.class);
    }

    @Override
    public void onBackPressed() {
        if (isMainActivity() || isLoginActivity()) {
            moveTaskToBack(true);
        } else {
            finish();
        }
    }



    public void error(String message) {
        toast(message);
    }

    public void error() {
        toast(getString(R.string.error));
    }


    public void snack() {
        Snackbar.make(getWindow().getDecorView().findViewById(android.R.id.content), getString(R.string.success), Snackbar.LENGTH_LONG).show();
    }


}
