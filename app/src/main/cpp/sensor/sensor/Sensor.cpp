
#include "Sensor.h"

#include "../utils/CHelper.h"

extern Sensor *sensor;

JNIEXPORT void JNICALL
Java_com_yunfeng_sensor_MainActivity_nativeOnSensorChangedRotation
        (JNIEnv *env, jclass type, jfloat x, jfloat y, jfloat z) {
    sensor->onSensorChangedRotation(x, y, z);
}

JNIEXPORT void JNICALL
Java_com_yunfeng_sensor_MainActivity_nativeOnSensorChangedRotationMatrix
        (JNIEnv *env, jclass type, jfloatArray rotationMatrix_) {
    jfloat *rotationMatrix = env->GetFloatArrayElements(rotationMatrix_, NULL);
    ndk_helper::Mat4 rotationMax4 = rotationMatrix;
    sensor->onSensorChangedRotation(rotationMax4);
    env->ReleaseFloatArrayElements(rotationMatrix_, rotationMatrix, 0);
}

Sensor::Sensor() {
    Init();
}

Sensor::~Sensor() {

}

void Sensor::Init() {
    EGLDisplay display_ = eglGetDisplay(EGL_DEFAULT_DISPLAY);
    eglInitialize(display_, 0, 0);

    /*
     * Here specify the attributes of the desired configuration.
     * Below, we select an EGLConfig with at least 8 bits per color
     * component compatible with on-screen windows
     */
    const EGLint attribs[] = {EGL_RENDERABLE_TYPE,
                              EGL_OPENGL_ES2_BIT, //Request opengl ES2.0
                              EGL_SURFACE_TYPE, EGL_WINDOW_BIT, EGL_BLUE_SIZE, 8, EGL_GREEN_SIZE, 8,
                              EGL_RED_SIZE, 8, EGL_DEPTH_SIZE, 24, EGL_NONE};
    int32_t color_size_ = 8;
    int32_t depth_size_ = 24;
    EGLConfig config_;
    EGLint num_configs;
    eglChooseConfig(display_, attribs, &config_, 1, &num_configs);

    if (!num_configs) {
        //Fall back to 16bit depth buffer
        const EGLint attribs[] = {EGL_RENDERABLE_TYPE,
                                  EGL_OPENGL_ES2_BIT, //Request opengl ES2.0
                                  EGL_SURFACE_TYPE, EGL_WINDOW_BIT, EGL_BLUE_SIZE, 8,
                                  EGL_GREEN_SIZE, 8,
                                  EGL_RED_SIZE, 8, EGL_DEPTH_SIZE, 16, EGL_NONE};
        eglChooseConfig(display_, attribs, &config_, 1, &num_configs);
        depth_size_ = 16;
    }

    if (!num_configs) {
        LOGI("Unable to retrieve EGL config");
        return;
    }
    EGLint format;
    eglGetConfigAttrib(display_, config_, EGL_NATIVE_VISUAL_ID, &format);
}

void Sensor::onSurfaceCreated(AAssetManager *pManager) {
    aAssetManager = pManager;
    teapotRenderer = new TeapotRenderer(aAssetManager);
    camera = new ndk_helper::TapCamera;
    teapotRenderer->Init();
    teapotRenderer->Bind(camera);

    // Initialize GL state.
    glEnable(GL_CULL_FACE);
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LEQUAL);

    camera->SetFlip(1.f, -1.f, -1.f);
    camera->SetPinchTransformFactor(2.f, 2.f, 8.f);
    monitor_ = new ndk_helper::PerfMonitor;

    LOGI("Sensor::onSurfaceCreated");
}

void Sensor::onDrawFrame() {
    float fps;
    monitor_->Update(fps);
//    LOGI("Sensor::onDrawFrame: %f", fps);
    teapotRenderer->Update(monitor_->GetCurrentTime());
    // Just fill the screen with a color.
    glClearColor(0.5f, 0.5f, 0.5f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    teapotRenderer->Render();
}

void Sensor::onSurFaceChanged(jint width, jint height) {
    //Note that screen size might have been changed
    glViewport(0, 0, width, height);
    teapotRenderer->UpdateViewport();
    LOGI("Sensor::onSurFaceChanged, %d, %d", width, height);
}

const ndk_helper::Vec3 ZERO_VEC3 = ndk_helper::Vec3(0.0f, 0.0f, 0.0f);

void Sensor::onSensorChangedRotation(float x, float y, float z) {
    ndk_helper::Vec3 newVec(x, y, z);
//    LOGI("Rotation: %f, %f, %f", x, y, z);
    if (temp == ZERO_VEC3) {
        temp = newVec;
    } else {
        ndk_helper::Vec3 delta_vec3 = (newVec - temp) * 0.01;
        teapotRenderer->Rotation(delta_vec3);
        temp = newVec;
    }

}

void Sensor::onSensorChangedRotation(ndk_helper::Mat4 mat4) {
    teapotRenderer->Rotation(mat4);
}
