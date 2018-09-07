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

private:
    AAssetManager *aAssetManager;
    TeapotRenderer *teapotRenderer;
    ndk_helper::TapCamera *camera;
    ndk_helper::PerfMonitor *monitor_;

    mutable pthread_mutex_t mutex_;
};
