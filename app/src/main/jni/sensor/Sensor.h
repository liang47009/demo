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

    bool ReadFile(const char *fileName, std::vector<uint8_t> *buffer_ref);

    void onSensorChangedRotation(float x, float y, float z);

    void onSensorChangedRotation(ndk_helper::Mat4 mat4);

private:
    AAssetManager *aAssetManager;
    TeapotRenderer *teapotRenderer;
    ndk_helper::TapCamera *camera;
    ndk_helper::PerfMonitor *monitor_;
    ndk_helper::Vec3 temp;

    mutable pthread_mutex_t mutex_;
};
