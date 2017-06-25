package com.mall.android.common.utils;

import android.widget.Toast;

import com.mall.android.common.GlobalApp;

/**
 * 
 * ToastUtil 工具类
 * 
 * @author zhoubo
 * @version 1.0
 */
public class ToastUtil {
    
    private static Toast mToast;
    
    /**
     * 显示简单文本信息
     * @param msg
     */
    public static final void showMsg(final String msg) {
        
        if(mToast == null){
            
            mToast = Toast.makeText(GlobalApp.getInstance(), msg, Toast.LENGTH_LONG);
        }else {

            mToast.setText(msg);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        
        mToast.show();
    }
    
    /**
     * 取消显示
     */
    public static final void cancelMsg(){
        
        if (mToast != null) {
            mToast.cancel();
        }
    }
}