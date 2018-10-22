#include <jni.h>
#include <android/log.h>
#include "FmodSound.h"

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "APP", __VA_ARGS__)

extern "C"
{
JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *vm, void *reserved) {
}

JNIEXPORT void JNICALL Java_com_yunfeng_fmod_FModeActivity_nativeFmodStart(JNIEnv *, jclass) {
    FmodSound::getInstance()->start();
}
}
