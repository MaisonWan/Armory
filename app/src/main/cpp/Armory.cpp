//
// Created by 万里鹏 on 2017/12/6.
//
#include <jni.h>
#include "vm/VmEnv.h"

#define JNI_REG_CLASS "com/domker/armory/demo/NativeProxy"

/**
* Table of methods associated with a single class.
*/
static JNINativeMethod gMethods[] = {
        {"stringFromJNI",     "()Ljava/lang/String;", (void *) vm::stringFromJNI},
};


/*
* Register several native methods for one class.
*/
static int registerNativeMethods(JNIEnv *env, const char *className,
                                 JNINativeMethod *gMethods, int numMethods) {
    jclass clazz;
    clazz = env->FindClass(className);

    if (clazz == NULL) {
        return JNI_FALSE;
    }
    if (env->RegisterNatives(clazz, gMethods, numMethods) < 0) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

/*
* Register native methods for all classes we know about.
*/
static int registerNatives(JNIEnv *env) {
    if (!registerNativeMethods(env, JNI_REG_CLASS, gMethods, sizeof(gMethods) / sizeof(gMethods[0]))) {
        return JNI_FALSE;
    }
    return JNI_TRUE;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    if (!registerNatives(env)) {//注册
        return JNI_ERR;
    }
//    vm::initContext(vm, env);
    return JNI_VERSION_1_6;
}