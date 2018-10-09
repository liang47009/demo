//
// Created by xll on 2018/9/14.
//

#ifndef _UTILS_H__
#define _UTILS_H__

#include <android/log.h>
#include <vector>
#include "vecmath.h"

#ifndef LOGI
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, "YUNFENG", __VA_ARGS__)
#endif

class utils {
public:
    static float sTemp[];

    static bool ReadFile(void *, const char *, std::vector<uint8_t> *);

    static unsigned int loadTextureUseFreeImage(void *, const char *);

    static void
    rotateM(float rm[], int rmOffset, float m[], int mOffset, float a, float x, float y, float z);

    static void rotateM(float m[], int mOffset, float a, float x, float y, float z);

    static void arraycopy(float *src, int srcPos, float *dst, int dstPos, int length);

    static int loadTextureUseStb(void *, const char *);
};


#endif //_UTILS_H__
