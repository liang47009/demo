#ifndef _RENDERER_H_
#define _RENDERER_H_

#include <android/asset_manager.h>
#include "vecmath.h"
#include "Singleton.h"
#include "ProgrameId.h"
#include "Triangle.h"
#include "Square.h"

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
    Triangle m_triangle;
    Square m_square;
};

#endif//_RENDERER_H_