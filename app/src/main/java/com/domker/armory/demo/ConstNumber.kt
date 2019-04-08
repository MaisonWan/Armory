package com.domker.armory.demo

/**
 *
 * Created by wanlipeng on 2018/7/5 下午9:39
 */
class ConstNumber {

    fun getVersion(): Int {
        return Version
    }

    companion object {
        private val Version = 123
    }
}