//
// Created by xll on 2018/9/14.
//

#ifndef _SQUARE_H__
#define _SQUARE_H__

#include <vector>
#include <android/asset_manager.h>
#include "ProgrameId.h"
#include "vecmath.h"
#include "IGeometry.h"

class Square : public IGeometry {
public:
    Square();

    ~Square();

    bool init(void *pManager);

    void draw();

    void changed(int width, int height);

protected:
    int _textureId;
    int vPosition;
    int vTexCoord;
    int uMatrix;
    int fTexture;
    // 正方形的顶点坐标
    float *m_square_vertxs;
    // 纹理的顶点坐标
    float *m_texture_coords;
};


#endif //_SQUARE_H__
