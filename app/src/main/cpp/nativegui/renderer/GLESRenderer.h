#ifndef _RENDERER_H_
#define _RENDERER_H_

#include <android/asset_manager.h>
#include "vecmath.h"
#include "Singleton.h"
#include "ProgrameId.h"
#include "Triangle.h"
#include "Square.h"
#include "IRenderer.h"
#include "IGeometry.h"

class GLESRenderer : public Singleton<GLESRenderer>, IRenderer {
public:
    GLESRenderer();

    ~GLESRenderer();

    bool init(AAssetManager *pManager);

    bool onChanged(int width, int height);

    void update();

    void onDrawFrame();

private:
    AAssetManager *m_AAssetManager;
    std::vector<IGeometry *> geometrys;
};

#endif//_RENDERER_H_