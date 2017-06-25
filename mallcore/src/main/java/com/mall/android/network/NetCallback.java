package com.mall.android.network;

public abstract class NetCallback<T> {

    public abstract void onSuccess(int code, T t);

    public abstract void onFailure(int code, String msg);
}