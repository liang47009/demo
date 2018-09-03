#include <jni.h>
#include <dlfcn.h>
#include "Clazz.h"

typedef void (*func)(int);

extern "C"
{
Clazz *clazz;

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    clazz = new Clazz;
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    delete clazz;
}

JNIEXPORT void JNICALL Java_com_yunfeng_nativecall_MainActivity_test(JNIEnv *, jclass) {
    void *handle = dlopen("/data/data/com.yunfeng.demo/lib/libapp.so", RTLD_LAZY);
    const char *error = dlerror();
    LOGI("error: %s", error);
    if (handle) {
        func f = (func) dlsym(handle, "Test");
        error = dlerror();
        LOGI("error: %s", error);
        if (f) {
            f(5);
            Invoke<LoggingAspect>(f, 1);
        }
    }
}

}