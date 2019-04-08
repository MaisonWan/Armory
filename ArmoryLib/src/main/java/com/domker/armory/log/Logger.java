package com.domker.armory.log;

import android.util.Log;

import java.util.Locale;

/**
 * Created by wanlipeng on 2019/4/8 4:52 PM
 */
public final class Logger {
    public static final String TAG = "Armory";

    public static void log(String msg) {
        Log.i(TAG, msg);
    }

    public static void log(Object object) {
        if (object != null) {
            Log.i(TAG, object.toString());
        }
    }

    public static void log(Object clazzObject, String msg) {
        final String clazzName = clazzObject.getClass().getSimpleName();
        Log.i(TAG, String.format(Locale.getDefault(), "[%s] %s", clazzName, msg));
    }

    /**
     * 按照指定格式输入
     *
     * @param format 格式化参数
     * @param args   具体参数
     */
    public static void log(String format, Object... args) {
        Log.i(TAG, String.format(Locale.getDefault(), format, args));
    }
}
