//
// Created by xll on 2018/9/14.
//

#ifndef _SQUARE_H__
#define _SQUARE_H__

#include <vector>
#include <android/asset_manager.h>
#include "ProgrameId.h"
#include "vecmath.h"

class Square {
public:
    Square();

    ~Square();

    bool init(AAssetManager *pManager);

    void draw(Mat4 &mat4);

private:
    int _textureId;
    PROGRAM_P3_TEX _shader;

    Mat4 mTransformMatrix;

    // 正方形的顶点坐标
    float *m_square_coords;

    // 纹理的顶点坐标
    float *m_texture_coords;

};


#endif //_SQUARE_H__