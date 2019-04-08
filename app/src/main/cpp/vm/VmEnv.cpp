//
// Created by 万里鹏 on 2017/12/6.
//

#include <jni.h>
#include <string>
#include <sys/system_properties.h>
#include <dlfcn.h>
#include "VmEnv.h"
#include "../Log.h"

jstring vm::stringFromJNI(JNIEnv *env, jclass jclz) {
    std::string hello = "Hello from Jni Vm";
    return env->NewStringUTF(hello.c_str());
}

jint (*JNI_CreateJavaVM_) (JavaVM**, JNIEnv**, void*);

void vm::initContext(JavaVM *javaVM, JNIEnv *env) {
    const char* default_library = "libart.so";
    LOGD("default_library %s", default_library);
    void* handle_ = dlopen(default_library, RTLD_NOW);
    LOGD("handle_ %x", handle_);
    JNI_CreateJavaVM_ = (jint (*)(JavaVM **, JNIEnv **, void *)) dlsym(handle_, "");
    LOGD("JNI_CreateJavaVM_ %x", &JNI_CreateJavaVM_);
    JavaVMInitArgs args;
    int res = JNI_CreateJavaVM_(&javaVM, &env, (void*)&args);
    LOGD("JNI_CreateJavaVM res = %d", res);
}

//void resize(int size) {
//    JavaVMExt * vmExt = (JavaVMExt *)javaVM;
//    if (vmExt->runtime == NULL) {
//        return;
//    }
//    char* runtime_ptr = (char*) vmExt->runtime;
//    void** heap_pp = (void**)(runtime_ptr + 188);
//    char* c_heap = (char*) (*heap_pp);
//
//    char* min_free_offset = c_heap + 532;
//    char* max_free_offset = min_free_offset + 4;
//    char* target_utilization_offset = max_free_offset + 4;
//
//    size_t* min_free_ = (size_t*) min_free_offset;
//    size_t* max_free_ = (size_t*) max_free_offset;
//
//    *min_free_ = 1024 * 1024 * 2;
//    *max_free_ = 1024 * 1024 * 8;
//}