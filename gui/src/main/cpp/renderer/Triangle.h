//
// Created by xll on 2018/9/14.
//

#ifndef _TRIANGLE_H__
#define _TRIANGLE_H__


#include "ProgrameId.h"

class Triangle {
public:
    Triangle();

    ~Triangle();

    bool init();

    void render();

private:
    PROGRAM_Triangle _shader;
    int _textureId;
};


#endif //_TRIANGLE_H__
