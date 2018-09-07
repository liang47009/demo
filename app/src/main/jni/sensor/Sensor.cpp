
#include "Sensor.h"

#include <android/log.h>
#include <fstream>

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "APP", __VA_ARGS__)

Sensor::Sensor() {
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
        LOGW("Unable to retrieve EGL config");
        return;
    }
    EGLint format;
    eglGetConfigAttrib(display_, config_, EGL_NATIVE_VISUAL_ID, &format);
}

Sensor::~Sensor() {

}

//---------------------------------------------------------------------------
//readFile
//---------------------------------------------------------------------------
bool Sensor::ReadFile(const char *fileName, std::vector<uint8_t> *buffer_ref) {
    std::ifstream f(fileName, std::ios::binary);
    if (f) {
        LOGI("reading:%s", fileName);
        f.seekg(0, std::ifstream::end);
        int32_t fileSize = f.tellg();
        f.seekg(0, std::ifstream::beg);
        buffer_ref->reserve(fileSize);
        buffer_ref->assign(std::istreambuf_iterator<char>(f), std::istreambuf_iterator<char>());
        f.close();
        pthread_mutex_unlock(&mutex_);
        return true;
    } else {
        //Fallback to assetManager
        AAsset *assetFile = AAssetManager_open(aAssetManager, fileName, AASSET_MODE_BUFFER);
        if (!assetFile) {
            pthread_mutex_unlock(&mutex_);
            return false;
        }
        uint8_t *data = (uint8_t *) AAsset_getBuffer(assetFile);
        int32_t size = AAsset_getLength(assetFile);
        if (data == NULL) {
            AAsset_close(assetFile);

            LOGI("Failed to load:%s", fileName);
            pthread_mutex_unlock(&mutex_);
            return false;
        }
        buffer_ref->reserve(size);
        buffer_ref->assign(data, data + size);
        AAsset_close(assetFile);
        pthread_mutex_unlock(&mutex_);
        return true;
    }
}

void Sensor::onSurfaceCreated(AAssetManager *pManager) {
    aAssetManager = pManager;
    teapotRenderer = new TeapotRenderer;
    camera = new ndk_helper::TapCamera;
    teapotRenderer->Init(this);
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
    if (temp == ZERO_VEC3) {
        temp = newVec;
    } else {
        ndk_helper::Vec3 delta_vec3 = (newVec - temp) * 0.01;
        teapotRenderer->Rotation(delta_vec3);
        temp = newVec;
    }

}

