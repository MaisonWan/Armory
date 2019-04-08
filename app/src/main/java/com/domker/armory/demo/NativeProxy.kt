package com.domker.armory.demo

/**
 * Created by wanlipeng on 2017/12/6.
 */
object NativeProxy {
    // Used to load the 'native-lib' library on application startup.
    init {
        System.loadLibrary("vm")
    }

    external fun stringFromJNI(): String
}