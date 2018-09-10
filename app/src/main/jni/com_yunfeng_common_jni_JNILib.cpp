#include "com_yunfeng_common_jni_JNILib.h"

#include <android/asset_manager_jni.h>
#include <sensor/Sensor.h>

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

JNIEXPORT void JNICALL
Java_com_yunfeng_common_jni_JNILib_nativeOnSensorChangedRotation(JNIEnv *env, jclass type, jfloat x,
                                                                 jfloat y, jfloat z) {
    sensor->onSensorChangedRotation(x, y, z);
}

JNIEXPORT void JNICALL
Java_com_yunfeng_common_jni_JNILib_nativeOnSensorChangedRotationMatrix(JNIEnv *env, jclass type,
                                                                       jfloatArray rotationMatrix_) {
    jfloat *rotationMatrix = env->GetFloatArrayElements(rotationMatrix_, NULL);
    ndk_helper::Mat4 rotationMax4 = rotationMatrix;
    sensor->onSensorChangedRotation(rotationMax4);
    env->ReleaseFloatArrayElements(rotationMatrix_, rotationMatrix, 0);
}