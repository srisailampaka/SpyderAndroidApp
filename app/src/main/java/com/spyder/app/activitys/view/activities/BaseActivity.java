package com.spyder.app.activitys.view.activities;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.animation.RotateAnimation;

import com.spyder.app.activitys.R;
import com.spyder.app.activitys.presenter.SpyderContract;
import com.spyder.app.activitys.presenter.SpyderPresenter;

/**
 * Created by srisailampaka on 28/10/17.
 */
@SuppressWarnings("deprecation")
public abstract class BaseActivity extends AppCompatActivity implements SpyderContract.View {

    protected SpyderPresenter mDigCondoPresenter;
    public ProgressDialog progressDialog;

    Dialog dialog;
    RotateAnimation anim;
    LayoutInflater lyt_Inflater;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDigCondoPresenter = new SpyderPresenter(this, getApplicationContext());

    }



    public void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading...........");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(true);
        }
        progressDialog.show();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
    @Override
    protected void onPause() {
        super.onPause();

    }




    public void hideProgressDialog() {
     if (progressDialog != null) {
            progressDialog.dismiss();
        }

//        if (anim != null)
//            anim.cancel();
//        // anim.reset();
//
//        if (dialog != null && dialog.isShowing())
//            dialog.dismiss();

    }























}
