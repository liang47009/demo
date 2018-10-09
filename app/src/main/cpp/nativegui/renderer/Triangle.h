//
// Created by xll on 2018/9/14.
//

#ifndef _TRIANGLE_H__
#define _TRIANGLE_H__

#include "ProgrameId.h"
#include "vecmath.h"
#include "IGeometry.h"

class Triangle : public IGeometry {
public:
    Triangle();

    ~Triangle();

    bool init(void *pManager);

    void draw();

    void changed(int width, int height);

private:
    float *m_tri_vertxs;
    int _textureId;

    int _position;
    int _color;
    int _mvp ;
};


#endif //_TRIANGLE_H__
