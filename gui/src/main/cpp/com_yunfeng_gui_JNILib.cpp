#include <android/asset_manager_jni.h>
//#include <android/ndk-version.h>
#include "com_yunfeng_gui_JNILib.h"
#include "renderer/Renderer.h"
#include "renderer/utils.h"

extern "C" {

JNIEXPORT jint JNI_OnLoad(JavaVM *, void *) {
    LOGI("JNI_OnLoad");
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNI_OnUnload(JavaVM *, void *) {
    LOGI("JNI_OnUnload");
}

JNIEXPORT void JNICALL Java_com_yunfeng_gui_JNILib_nativeOnSurfaceCreated
        (JNIEnv *env, jclass, jobject assetManager) {
    AAssetManager *aAssetManager = AAssetManager_fromJava(env, assetManager);
    Renderer::getInstance()->init(aAssetManager);
}

JNIEXPORT void JNICALL Java_com_yunfeng_gui_JNILib_nativeOnSurfaceChanged
        (JNIEnv *, jclass, jint width, jint height) {
    Renderer::getInstance()->onChanged(width, height);
}

JNIEXPORT void JNICALL Java_com_yunfeng_gui_JNILib_nativeOnDrawFrame
        (JNIEnv *, jclass) {
    Renderer::getInstance()->onDrawFrame();
}

}
