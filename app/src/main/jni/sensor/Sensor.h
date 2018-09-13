#pragma once

#include <jni.h>
#include "TeapotRenderer.h"

class Sensor {
public:
    Sensor();

    ~Sensor();

    void onSurfaceCreated(AAssetManager *pManager);

    void onDrawFrame();

    void onSurFaceChanged(jint width, jint heidht);

    void onSensorChangedRotation(float x, float y, float z);

    void onSensorChangedRotation(ndk_helper::Mat4 mat4);

private:
    AAssetManager *aAssetManager;
    TeapotRenderer *teapotRenderer;
    ndk_helper::TapCamera *camera;
    ndk_helper::PerfMonitor *monitor_;
    ndk_helper::Vec3 temp;

    void Init();
};

extern "C" {

/*
 * Class:     com_yunfeng_common_jni_JNILib
 * Method:    nativeOnSensorChangedRotation
 * Signature: (FFF)V
 */
JNIEXPORT void JNICALL Java_com_yunfeng_sensor_MainActivity_nativeOnSensorChangedRotation
        (JNIEnv *env, jclass type, jfloat x, jfloat y, jfloat z);

JNIEXPORT void JNICALL Java_com_yunfeng_sensor_MainActivity_nativeOnSensorChangedRotationMatrix
        (JNIEnv *env, jclass type, jfloatArray rotationMatrix_);

}