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

    void draw(Mat4 &mat_model_);

    void changed(int width, int height);

private:
    PROGRAM_Tr_U1 _shader;
    int _textureId;
    Mat4 mProjectionMatrix;
    Mat4 mViewMatrix;
    Mat4 mRotationMatrix;

    float mAngle;

};


#endif //_TRIANGLE_H__