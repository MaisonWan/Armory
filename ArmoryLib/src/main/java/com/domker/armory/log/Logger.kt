package com.domker.armory.log

import android.util.Log
import com.domker.armory.view.ViewPrinter


fun log(msg: String) {
    Log.i(ViewPrinter.TAG, msg)
}