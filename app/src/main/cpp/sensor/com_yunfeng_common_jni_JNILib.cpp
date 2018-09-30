#include "com_yunfeng_common_jni_JNILib.h"

#include <android/asset_manager_jni.h>
#include "sensor/Sensor.h"
#include <dlfcn.h>

Sensor *sensor;

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    sensor = new Sensor;
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
    delete sensor;
}

JNIEXPORT void JNICALL Java_com_yunfeng_common_jni_JNILib_nativeOnSurfaceCreated
        (JNIEnv *env, jobject, jobject jobj) {
    AAssetManager *assetManager = AAssetManager_fromJava(env, jobj);
    sensor->onSurfaceCreated(assetManager);
}

JNIEXPORT void JNICALL Java_com_yunfeng_common_jni_JNILib_nativeOnDrawFrame
        (JNIEnv *, jobject) {
    sensor->onDrawFrame();
}

JNIEXPORT void JNICALL Java_com_yunfeng_common_jni_JNILib_nativeOnSurfaceChanged
        (JNIEnv *, jobject, jint width, jint height) {
    sensor->onSurFaceChanged(width, height);
}

JNIEXPORT long JNICALL
Java_com_yunfeng_common_jni_JNILib_nativeLoadLibrary
        (JNIEnv *env, jclass type, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);
    void *handle = dlopen(path, RTLD_LAZY);
    const char *error = dlerror();
    LOGI("error: %s", error);
    if (handle) {
        LOGI("handle: %p", handle);
    }
    env->ReleaseStringUTFChars(path_, path);
    return reinterpret_cast<long>(handle);
}

JNIEXPORT void JNICALL
Java_com_yunfeng_common_jni_JNILib_nativeUnloadLibrary
        (JNIEnv *, jclass, jlong handler) {
    if (handler) {
        int ret = dlclose(reinterpret_cast<void *>(handler));
        LOGI("nativeUnloadLibrary: ret=>%d", ret);
    }
}