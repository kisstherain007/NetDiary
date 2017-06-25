package com.mall.android.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.mall.android.R;
import com.mall.android.common.GlobalApp;
import com.mall.android.common.utils.NetworkHelper;
import com.mall.android.common.utils.ToastUtil;
import com.mall.android.network.NetCallback;
import com.mall.android.network.OkHttp3Utils;

/**
 * Created by zhoubo on 2017/2/18.
 */

public abstract class BaseFragment extends Fragment {

    Toolbar mToolbar;

    LayoutInflater mLayoutInflater;

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public LayoutInflater getLayoutInflater() {
        return mLayoutInflater;
    }

    private ViewGroup rootView;// 根视图

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(inflateContentView(), null);
        rootView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        this.mLayoutInflater = LayoutInflater.from(getContext());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mToolbar = (Toolbar) view.findViewById(R.id.toolbar);
    }

    private boolean initNetWorkCheck(){
        if (!NetworkHelper.isNetworkAvailable(GlobalApp.getInstance())) {
//            ToastUtil.showMsg(getResources().getString(R.string.not_availble_net_work));
            return false;
        }
        return true;
    }

    public <T> void executeGetNetRequest(String url, final Class<?> clazz, final NetCallback<T> callback){

        if(!initNetWorkCheck()) return;

        OkHttp3Utils.getInstance(GlobalApp.getInstance()).doGet(url, null, null, new NetCallback<String>(){

            @Override
            public void onSuccess(int code, String response) {
                if (callback != null) {
                    T t = getJsonObj(response, clazz);
                    if (t != null){
                        callback.onSuccess(code, t);
                    }else {
                        callback.onFailure(-1, "数据解析为空");
                    }
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                if (callback != null) callback.onFailure(code, msg);
            }
        });
    }

    public <T> void executePostNetRequest(String url, final Class<?> clazz, final NetCallback<T> callback){

        if(!initNetWorkCheck()) return;

        OkHttp3Utils.getInstance(GlobalApp.getInstance()).doGet(url, null, null, new NetCallback<String>(){

            @Override
            public void onSuccess(int code, String response) {
                if (callback != null) {
                    T t = getJsonObj(response, clazz);
                    if (t != null){
                        callback.onSuccess(code, t);
                    }else {
                        callback.onFailure(-1, "数据解析为空");
                    }
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                if (callback != null) callback.onFailure(code, msg);
            }
        });
    }

    public <T> T getJsonObj(String json, Class<?> pClass){
        try {
            return (T) new GsonBuilder().create().fromJson(json, pClass);
        } catch (JsonSyntaxException e) {
            ToastUtil.showMsg("数据解析异常！");
            return null;
        }
    }

    public void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    public void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(getActivity(), pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    abstract protected int inflateContentView();

    public void requestData(){

    }
}
