package com.mysql.qi_fu.librarymanage.retrofit.api;

/**
 * Created by qi_fu on 2017/4/13.
 */

public interface NetResultCallback<E> {

    void onStart();

    void onSuccess(E data);

    void onFailed(int code, String msg);

    void cancel();
}
