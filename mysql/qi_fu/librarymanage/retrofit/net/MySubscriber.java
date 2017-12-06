package com.mysql.qi_fu.librarymanage.retrofit.net;

import android.app.ProgressDialog;
import android.content.Context;

import rx.Subscriber;

/**
 * Created by peter on 2016/9/19.
 */

public abstract class MySubscriber<T> extends Subscriber<T> {

    private Context context;
    private ProgressDialog mProgressDialog;

    protected MySubscriber(Context context) {
        super();
        this.context = context;
        mProgressDialog = new ProgressDialog(context);
    }

    protected MySubscriber() {
        super();
    }

    public void showProgressBar() {
        if (mProgressDialog != null && !mProgressDialog.isShowing())
            mProgressDialog.show();
    }

    public void stopProgressBar() {
        if (mProgressDialog != null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressBar();
    }

    @Override
    public void onCompleted() {
        stopProgressBar();
    }

    @Override
    public void onError(Throwable e) {
        stopProgressBar();
    }


 /*   @Override
    public void onError(Throwable e) {
        Logger.e("onError:" + e.getMessage());
        stopProgressBar();
    }*/
}
