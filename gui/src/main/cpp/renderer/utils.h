//
// Created by xll on 2018/9/14.
//

#ifndef _UTILS_H__
#define _UTILS_H__

#include <android/asset_manager.h>
#include <FreeImage.h>
#include <vector>
#include "vecmath.h"

class utils {
public:
    static float sTemp[];

    static bool ReadFile(AAssetManager *, const char *, std::vector<uint8_t> *);

    static unsigned int loadTexture(AAssetManager *, const char *);

    static void
    rotateM(float rm[], int rmOffset, float m[], int mOffset, float a, float x, float y, float z);

    static void rotateM(float m[], int mOffset, float a, float x, float y, float z);

    static void arraycopy(float *src, int srcPos, float *dst, int dstPos, int length);
};


#endif //_UTILS_H__
