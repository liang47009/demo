#include <jni.h>
#include <dlfcn.h>

#include <android/log.h>

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "APP", __VA_ARGS__)

typedef void (*func)(void);

extern "C"
{
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
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
            f();
        }
    }
}
}
