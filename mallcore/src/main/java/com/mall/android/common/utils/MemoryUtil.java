package com.mall.android.common.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.util.Log;

import com.mall.android.common.GlobalApp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhoubo on 2017/3/8.
 */

public class MemoryUtil {

    private static final String TAG = MemoryUtil.class.getSimpleName();
    /**
     * 得到系统总内存 单位KB
     *
     * @param context
     * @return
     */
    public long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";// 系统内存信息文件
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;

        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

            arrayOfString = str2.split("\\s+");
         /*   for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }*/

            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
            localBufferedReader.close();

        } catch (IOException e) {
        }
        return initial_memory;
    }

    public long getAvailMemory(Context context) {// 获取android当前可用内存大小
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem;
    }

    public int getPrecent() {
        long totalSize = getTotalMemory(GlobalApp.getInstance());
        long aliSize = getAvailMemory(GlobalApp.getInstance());
        int precent = 100 - (int) (aliSize * 100 / (float) totalSize);
        return precent;
    }

    /**
     * Android获取应用所占内存大小
     */
    private void getRunningAppProcessInfo() {

        ActivityManager mActivityManager = (ActivityManager) GlobalApp.getInstance().getSystemService(
                Context.ACTIVITY_SERVICE);

        // 获得系统里正在运行的所有进程
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessesList = mActivityManager
                .getRunningAppProcesses();

        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcessesList) {
            // 进程ID号
            int pid = runningAppProcessInfo.pid;
            // 用户ID
            int uid = runningAppProcessInfo.uid;
            // 进程名
            String processName = runningAppProcessInfo.processName;
            // 占用的内存
            int[] pids = new int[]{pid};
            Debug.MemoryInfo[] memoryInfo = mActivityManager
                    .getProcessMemoryInfo(pids);
            int memorySize = memoryInfo[0].dalvikPrivateDirty;

            Logger.d(TAG, "processName=" + processName + ",pid=" + pid + ",uid="
                    + uid + ",memorySize=" + memorySize + "kb");
        }
    }

    /**
     * 2   * 获取单个 app 内存限制大小
     * 3   * 返回以 M 为单位的数字，
     * 4   * 可能在不同的平台或者设备上值都不太一样
     * 5
     */
    public int getCurrentMemorySize() {
        ActivityManager activityManager2 = (ActivityManager) GlobalApp.getInstance().getSystemService(
                Context.ACTIVITY_SERVICE);
        int size = activityManager2.getMemoryClass();
        return size;
    }

}
