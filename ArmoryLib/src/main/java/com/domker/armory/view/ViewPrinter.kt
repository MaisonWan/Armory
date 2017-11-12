package com.domker.armory.view

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.domker.armory.log.log

/**
 * 打印View信息
 * Created by wanlipeng on 2017/11/8.
 */

object ViewPrinter {
    val TAG = "ViewPrinter"

    /**
     * 打印从给定View到根view的信息
     * @param view
     */
    fun printViewPath(view: View) {
        var parent: View? = view
        var i = 1
        log("Print view tree from " + view)
        do {
            val drawable = parent!!.background
            val msg = "[$i] $parent"
            log(msg + " " + getDrawableInfo(drawable))
            // 上一层
            parent = if (parent.parent != null && parent.parent is View) {
                parent.parent as View
            } else {
                null
            }
        } while (parent != null)
    }

    /**
     * 获取Drawable的信息
     */
    private fun getDrawableInfo(drawable: Drawable?): String {
        return if (drawable == null) "" else when (drawable) {
            is ColorDrawable -> {
                val color = Integer.toHexString(drawable.color)
                "background: $drawable color: $color"
            }
            else -> {
                "background: $drawable"
            }
        }
    }

    /**
     * 打印View Tree，从给定ViewGroup开始，打印层级结构
     * @param view
     */
    fun printViewTree(view: View) {
        printViewTreeItem(view, "")
    }

    private fun printViewTreeItem(view: View?, prefixIndent: String) {
        if (view != null) {
            val clazz = view.javaClass.name
            log("$prefixIndent<$clazz> $view $view.background")
            if (view is ViewGroup) {
                val viewGroup = view as ViewGroup?
                val count = viewGroup!!.childCount
                (0 until count)
                        .map { viewGroup.getChildAt(it) }
                        .forEach { printViewTreeItem(it, prefixIndent + " ") }
            }
            log("$prefixIndent</$clazz>")
        }
    }

    /**
     * 打印当前线程中调用时间点的调用栈
     */
    fun printStackTrace() {
        Thread.currentThread().stackTrace.map { Log.i(TAG, it.toString()) }
    }
}
