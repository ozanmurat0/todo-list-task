package com.example.mytodolist.modules;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;

import com.example.mytodolist.R;
import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProgressLoaderManager {
    private static final ProgressLoaderManager mInstance = new ProgressLoaderManager();

    private LoaderDialog mDialog;

    private boolean isShowing = false;

    private ProgressLoaderManager() {}

    public static ProgressLoaderManager getInstance() {
        return mInstance;
    }

    public void show(final @NonNull Context context) {
        if(!isShowing()) {
            try {
                mDialog = new LoaderDialog(context);
                mDialog.show();
                isShowing = true;
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void hide() {
        try {
            if (mDialog != null) {
                mDialog.dismiss();
                mDialog = null;
            }
            isShowing = false;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean isShowing() {
        return this.isShowing;
    }

    static final class LoaderDialog extends Dialog {

        @BindView(R.id.loader)
        AVLoadingIndicatorView mLoader;

        LoaderDialog(final @NonNull Context context) {
            super(context, R.style.TransparentAlertDialogStyle);
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.layout_progress_loader);
            ButterKnife.bind(this);
            setCancelable(false);
            setCanceledOnTouchOutside(false);
        }

    }
}
