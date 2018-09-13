#ifndef _RENDERER_H_
#define _RENDERER_H_

#include <vecmath.h>
#include "Singleton.h"
#include "ProgrameId.h"

using namespace ndk_helper;

class Renderer : public Singleton<Renderer> {
public:
    Renderer();

    ~Renderer();

    bool init(AAssetManager *pManager);

    bool onChanged(int width, int height);

    void update();

    void onDrawFrame();

private:
    AAssetManager *m_AAssetManager;
    Mat4 mat_projection_;
    Mat4 mat_view_;
    Mat4 mat_model_;
    PROGRAM_P3_T2_C3 _shader;
    GLuint _textureId;
};

#endif//_RENDERER_H_