package com.domker.armory.framework

import com.domker.armory.framework.Reflect.findField
import com.domker.armory.log.log
import java.lang.reflect.Field

/**
 * Created by wanlipeng on 2017/11/12.
 */
object DexPath {

    /**
     * 打印当年运行时，系统的DexPathList里面包含的元素
     */
    fun printDexPathList(classLoader: ClassLoader) {
        val pathListField: Field
        try {
            pathListField = findField(classLoader, "pathList")
            val dexPathList = pathListField.get(classLoader)
            val dexElementsField = findField(dexPathList, "dexElements")
            val dexElements = dexElementsField.get(dexPathList) as Array<Any>
            log("Print DexPathList Size=" + dexElements.size)
            var i = 0
            dexElements.forEach { log("[${i++}] " + it) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}