//
// Created by xll on 2018/9/14.
//

#ifndef _TRIANGLE_H__
#define _TRIANGLE_H__

#include <android/asset_manager.h>
#include "ProgrameId.h"
#include "vecmath.h"

class Triangle {
public:
    Triangle();

    ~Triangle();

    bool init(AAssetManager *pManager);

    void draw();

    void changed(int width, int height);

private:
    Mat4 mTransformMatrix;
    PROGRAM_Tr_U1 _shader;
    int _textureId;
    float mAngle;
};


#endif //_TRIANGLE_H__
