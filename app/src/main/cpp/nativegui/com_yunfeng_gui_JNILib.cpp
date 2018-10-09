#include <android/asset_manager_jni.h>
//#include <android/ndk-version.h>
#include "com_yunfeng_gui_JNILib.h"
#include "renderer/GLESRenderer.h"
#include "renderer/utils.h"

#include <AndroidCrashDump.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jint JNI_OnLoad(JavaVM *env, void *) {
    LOGI("JNI_OnLoad");
    initAndroidCrashDump("/sdcard");
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *, void *) {
    LOGI("JNI_OnUnload");
}

JNIEXPORT void JNICALL Java_com_yunfeng_guinative_JNILib_nativeOnSurfaceCreated
        (JNIEnv *env, jclass, jobject assetManager) {
    AAssetManager *aAssetManager = AAssetManager_fromJava(env, assetManager);
    GLESRenderer::getInstance()->init(aAssetManager);
}

JNIEXPORT void JNICALL Java_com_yunfeng_guinative_JNILib_nativeOnSurfaceChanged
        (JNIEnv *, jclass, jint width, jint height) {
    GLESRenderer::getInstance()->onChanged(width, height);
}

JNIEXPORT void JNICALL Java_com_yunfeng_guinative_JNILib_nativeOnDrawFrame
        (JNIEnv *, jclass) {
    GLESRenderer::getInstance()->onDrawFrame();
}

#ifdef __cplusplus
}
#endif
