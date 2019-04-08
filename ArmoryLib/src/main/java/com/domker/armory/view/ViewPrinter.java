package com.domker.armory.view;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.Locale;

import static com.domker.armory.log.Logger.log;

/**
 * 打印View信息
 * <p>
 * Created by wanlipeng on 2019/4/8 4:40 PM
 */
public final class ViewPrinter {
    private static final String TAG = "ViewPrinter";

    /**
     * 打印从给定View到根view的信息
     *
     * @param view
     */
    public static void printViewPath(@Nullable View view) {
        int i = 1;
        log("Print view tree from " + view);
        while (view != null) {
            Drawable drawable = view.getBackground();
            String msg = String.format(Locale.getDefault(), "[%d] %s", i++, view);
            log(msg + " " + getDrawableInfo(drawable));
            // 上一层
            if (view.getParent() instanceof View) {
                view = (View) view.getParent();
            } else {
                view = null;
            }
        }
    }

    /**
     * 获取Drawable的信息
     */
    private static String getDrawableInfo(Drawable drawable) {
        if (drawable == null) {
            return "";
        }
        if (drawable instanceof ColorDrawable) {
            final String color = Integer.toHexString(((ColorDrawable) drawable).getColor());
            return "background: " + drawable + " color: " + color;
        } else {
            return "background: $drawable";
        }
    }

    /**
     * 打印View Tree，从给定ViewGroup开始，打印层级结构
     *
     * @param view
     */
    public static void printViewTree(View view) {
        printViewTreeItem(view, "");
    }

    private static void printViewTreeItem(View view, String prefixIndent) {
        if (view == null) {
            return;
        }
        String clazz = view.getClass().getSimpleName();
        log("%s<%s> %s %s", prefixIndent, clazz, view, view.getBackground());
        if (view instanceof ViewGroup && ((ViewGroup) view).getChildCount() > 0) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                printViewTreeItem(((ViewGroup) view).getChildAt(i), prefixIndent + "  ");
            }
        }
        log("%s</%s>", prefixIndent, clazz);
    }

    /**
     * 打印当前线程中调用时间点的调用栈
     */
    public static void printStackTrace() {
        final StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (StackTraceElement element : elements) {
            Log.i(TAG, element.toString());
        }
    }
}
