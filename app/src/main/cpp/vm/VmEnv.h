//
// Created by 万里鹏 on 2017/12/6.
//

#ifndef ARMORY_VMENV_H
#define ARMORY_VMENV_H

#include <jni.h>

namespace vm {
    jstring stringFromJNI(JNIEnv *env, jclass jclz);

    void initContext(JavaVM *vm, JNIEnv *env);
}

#endif //ARMORY_VMENV_H
