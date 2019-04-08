package com.domker.armory.framework;

import static com.domker.armory.log.Logger.log;

/**
 * 对于dex的信息打印
 * <p>
 * Created by wanlipeng on 2019/4/8 5:14 PM
 */
public final class DexPath {

    /**
     * 打印当年运行时，系统的DexPathList里面包含的元素
     */
    public static void printDexPathList(ClassLoader classLoader) {
        try {
            Object dexPathList = Reflect.getFieldNoException(classLoader, "pathList");
            Object[] dexElements = Reflect.getFieldNoException(dexPathList, "dexElements");
            log("Print DexPathList Size is " + dexElements.length);
            for (int i = 0; i < dexElements.length; i++) {
                log("[%d] %s", i, dexElements[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
