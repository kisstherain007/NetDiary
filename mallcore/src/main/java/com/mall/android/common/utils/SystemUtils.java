//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.mall.android.common.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Environment;
import android.os.StatFs;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.mall.android.common.GlobalApp;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

@SuppressLint({"SdCardPath"})
public class SystemUtils {
    private static int screenWidth;
    private static int screenHeight;
    private static float density;
    private static final String STATUS_BAR_HEIGHT_RES_NAME = "status_bar_height";
    private static final String NAV_BAR_HEIGHT_RES_NAME = "navigation_bar_height";
    private static final String NAV_BAR_HEIGHT_LANDSCAPE_RES_NAME = "navigation_bar_height_landscape";
    private static final String SHOW_NAV_BAR_RES_NAME = "config_showNavigationBar";
    private static final String NAV_BAR_WIDTH_RES_NAME = "navigation_bar_width";
    private static String sNavBarOverride;

    public SystemUtils() {
    }

    private static void setScreenInfo(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager windowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        density = dm.density;
    }

    public static int getScreenWidth(Context context) {
        if(screenWidth == 0) {
            setScreenInfo(context);
        }

        return screenWidth;
    }

    public static int getScreenHeight(Context context) {
        if(screenHeight == 0) {
            setScreenInfo(context);
        }

        return screenHeight;
    }

//    public static int getTitleBarHeight(Activity activity) {
//        int contentTop = activity.getWindow().findViewById(16908290).getTop();
//        int titleBarHeight = contentTop - getStatusBarHeight(activity);
//        return titleBarHeight;
//    }

    public static float getDensity(Context context) {
        if(density == 0.0F) {
            setScreenInfo(context);
        }

        return density;
    }

    public static boolean hasSDCard() {
        boolean mHasSDcard = false;
        if("mounted".endsWith(Environment.getExternalStorageState())) {
            mHasSDcard = true;
        } else {
            mHasSDcard = false;
        }

        return mHasSDcard;
    }

    public static String getSdcardPath() {
        return hasSDCard()?Environment.getExternalStorageDirectory().getAbsolutePath():"/sdcard/";
    }

    private static boolean sdcardCanWrite() {
        return Environment.getExternalStorageDirectory().canWrite();
    }

    public static boolean hasSdcardAndCanWrite() {
        return hasSDCard() && sdcardCanWrite();
    }

    public long getSdcardtAvailableStore() {
        if(hasSdcardAndCanWrite()) {
            String path = getSdcardPath();
            if(path != null) {
                StatFs statFs = new StatFs(path);
                long blocSize = (long)statFs.getBlockSize();
                long availaBlock = (long)statFs.getAvailableBlocks();
                return availaBlock * blocSize;
            }
        }

        return 0L;
    }

    public static SystemUtils.NetWorkType getNetworkType(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if(networkInfo != null) {
            switch(networkInfo.getType()) {
            case 0:
                return SystemUtils.NetWorkType.mobile;
            case 1:
                return SystemUtils.NetWorkType.wifi;
            }
        }

        return SystemUtils.NetWorkType.none;
    }

    public static String getMacAddress(Context context) {
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        return wifiInfo != null && wifiInfo.getMacAddress() != null?wifiInfo.getMacAddress().replace(":", ""):"0022f420d03f";
    }

    public static String getUDPIP(Context context) {
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        DhcpInfo dhcpInfo = wifi.getDhcpInfo();
        int IpAddress = dhcpInfo.ipAddress;
        int subMask = dhcpInfo.netmask;
        return transformIp(~subMask | IpAddress);
    }

    private static String transformIp(int i) {
        return (i & 255) + "." + (i >> 8 & 255) + "." + (i >> 16 & 255) + "." + (i >> 24 & 255);
    }

    public static String getIP(Context context) {
        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        return transformIp(wifi.getConnectionInfo().getIpAddress());
    }

    public static String getVersionName(Context context) {
        try {
            PackageManager e = context.getPackageManager();
            PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception var3) {
            return "";
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageManager e = context.getPackageManager();
            PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception var3) {
            return 0;
        }
    }

    public static String getPackage(Context context) {
        try {
            PackageManager e = context.getPackageManager();
            PackageInfo packageInfo = e.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception var3) {
            return "";
        }
    }

    public static void scanPhoto(Context context, File file) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        context.sendBroadcast(intent);
    }

    public static void hideSoftInput(Context context, View paramEditText) {
        ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(paramEditText.getWindowToken(), 0);
    }

    public static void showKeyBoard(final Context context, final View paramEditText) {
        paramEditText.requestFocus();
        paramEditText.post(new Runnable() {
            public void run() {
                ((InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(paramEditText, 0);
            }
        });
    }

    public static int getScreenHeight(Activity paramActivity) {
        Display display = paramActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        return metrics.heightPixels;
    }

//    public static int getKeyboardHeight(Activity paramActivity) {
//        int height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity) - getAppHeight(paramActivity);
//        if(height == 0) {
//            height = ActivityHelper.getIntShareData(GlobalApp.getInstance(), "KeyboardHeight", 400);
//        } else {
//            ActivityHelper.putIntShareData(GlobalContext.getInstance(), "KeyboardHeight", height);
//        }
//
//        return height;
//    }

    public static boolean isKeyBoardShow(Activity paramActivity) {
        int height = getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity) - getAppHeight(paramActivity);
        return height != 0;
    }

    @TargetApi(14)
    public static int getActionBarHeight(Context context) {
        int result = 0;
        if(VERSION.SDK_INT >= 14) {
            TypedValue tv = new TypedValue();
            context.getTheme().resolveAttribute(16843499, tv, true);
            result = TypedValue.complexToDimensionPixelSize(tv.data, context.getResources().getDisplayMetrics());
        }

        return result;
    }

    public static boolean inPortarit(Resources res) {
        return res.getConfiguration().orientation == 1;
    }

    @TargetApi(14)
    public static int getNavigationBarHeight(Context context) {
        Resources res = context.getResources();
        byte result = 0;
        if(VERSION.SDK_INT >= 14 && hasNavigationBar(context)) {
            String key;
            if(inPortarit(res)) {
                key = "navigation_bar_height";
            } else {
                key = "navigation_bar_height_landscape";
            }

            return getInternalDimensionSize(res, key);
        } else {
            return result;
        }
    }

    @TargetApi(14)
    public static int getNavigationBarWidth(Context context) {
        Resources res = context.getResources();
        byte result = 0;
        return VERSION.SDK_INT >= 14 && hasNavigationBar(context)?getInternalDimensionSize(res, "navigation_bar_width"):result;
    }

    @TargetApi(14)
    public static boolean hasNavigationBar(Context context) {
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android");
        if(resourceId != 0) {
            boolean hasNav = res.getBoolean(resourceId);
            if("1".equals(sNavBarOverride)) {
                hasNav = false;
            } else if("0".equals(sNavBarOverride)) {
                hasNav = true;
            }

            return hasNav;
        } else {
            return !ViewConfiguration.get(context).hasPermanentMenuKey();
        }
    }

    public static int getStatusBarHeight(Context context) {
        return getInternalDimensionSize(context.getResources(), "status_bar_height");
    }

//    public static int getAppContentHeight(Activity paramActivity) {
//        return getScreenHeight(paramActivity) - getStatusBarHeight(paramActivity) - getActionBarHeight(paramActivity) - getKeyboardHeight(paramActivity);
//    }

    public static int getAppHeight(Activity paramActivity) {
        Rect localRect = new Rect();
        paramActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(localRect);
        return localRect.height();
    }

    private static int getInternalDimensionSize(Resources res, String key) {
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if(resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }

        return result;
    }

    public static void startActivity(Activity context, String packageName) {
        try {
            Log.e("startActivity", packageName);
            Intent e = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if(e == null) {
                startActivityByApplication(context, packageName);
                return;
            }

            context.startActivity(e);
        } catch (Exception var3) {
            Log.e(SystemUtils.class.getSimpleName(), var3.toString());
        }

    }

    private static void startActivityByApplication(Context context, String packageNameStr) {
        try {
            PackageManager e = context.getPackageManager();
            PackageInfo pi = e.getPackageInfo(packageNameStr, 0);
            Intent resolveIntent = new Intent("android.intent.action.MAIN", (Uri)null);
            resolveIntent.addCategory("android.intent.category.LAUNCHER");
            resolveIntent.setPackage(pi.packageName);
            List apps = e.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = (ResolveInfo)apps.iterator().next();
            if(ri != null) {
                String packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.LAUNCHER");
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                context.startActivity(intent);
                return;
            }
        } catch (Exception var11) {
            ;
        }

    }

    static {
        if(VERSION.SDK_INT >= 19) {
            try {
                Class e = Class.forName("android.os.SystemProperties");
                Method m = e.getDeclaredMethod("get", new Class[]{String.class});
                m.setAccessible(true);
                sNavBarOverride = (String)m.invoke((Object)null, new Object[]{"qemu.hw.mainkeys"});
            } catch (Throwable var2) {
                sNavBarOverride = null;
            }
        }

    }

    public static enum NetWorkType {
        none,
        mobile,
        wifi;

        private NetWorkType() {
        }
    }
}
