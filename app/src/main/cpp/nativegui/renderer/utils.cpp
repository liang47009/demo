//
// Created by xll on 2018/9/14.
//
#include <fstream>
#include <GLES2/gl2.h>
#include <android/asset_manager.h>
#include "utils.h"

#ifdef USE_FREEIMAGE
#include <freeimage/Source/FreeImage.h>
#else
#endif

#define STB_IMAGE_IMPLEMENTATION

#include "stb_image.h"

bool utils::ReadFile(void *aAssetManager, const char *fileName,
                     std::vector<uint8_t> *buffer_ref) {
    std::ifstream f(fileName, std::ios::binary);
    if (f) {
//        LOGI("reading:%s", fileName);
        f.seekg(0, std::ifstream::end);
        std::fpos<mbstate_t> fileSize = f.tellg();
        f.seekg(0, std::ifstream::beg);
        buffer_ref->reserve(fileSize);
        buffer_ref->assign(std::istreambuf_iterator<char>(f), std::istreambuf_iterator<char>());
        f.close();
        return true;
    } else {
        if (NULL == aAssetManager) {
//            LOGE("AssetManager is null!");
            return false;
        }
        AAssetManager *temp = (AAssetManager *) aAssetManager;
        //Fallback to assetManager
        AAsset *assetFile = AAssetManager_open(temp, fileName, AASSET_MODE_BUFFER);
        if (!assetFile) {
//            LOGE("assetFile is null: %s", fileName);
            return false;
        }
        uint8_t *data = (uint8_t *) AAsset_getBuffer(assetFile);
        int32_t size = AAsset_getLength(assetFile);
        if (data == NULL) {
            AAsset_close(assetFile);
//            LOGE("Failed to load:%s", fileName);
            return false;
        }
        buffer_ref->reserve(size);
        buffer_ref->assign(data, data + size);
        AAsset_close(assetFile);
        return true;
    }
}

/**
 * Rotates matrix m by angle a (in degrees) around the axis (x, y, z).
 * <p>
 * m and rm must not overlap.
 *
 * @param rm returns the result
 * @param rmOffset index into rm where the result matrix starts
 * @param m source matrix
 * @param mOffset index into m where the source matrix starts
 * @param a angle to rotate in degrees
 * @param x X axis component
 * @param y Y axis component
 * @param z Z axis component
 */
void
utils::rotateM(float rm[], int rmOffset, float m[], int mOffset, float a, float x, float y,
               float z) {
    Mat4::setRotateM(sTemp, 0, a, x, y, z);
    Mat4::multiplyMM(rm, rmOffset, m, mOffset, sTemp, 0);
}

template<class T>
int length(T &arr) {
    //cout << sizeof(arr[0]) << endl;
    //cout << sizeof(arr) << endl;
    return sizeof(arr) / sizeof(arr[0]);
}

void utils::arraycopy(float src[], int srcPos, float dst[], int dstPos, int length) {
    if (src == NULL) {
        //
        return;
    }
    if (dst == NULL) {
        //
        return;
    }
    if (srcPos < 0 || dstPos < 0 || length < 0 ||
        srcPos > (sizeof(src) / sizeof(src[0])) - length ||
        dstPos > (sizeof(dst) / sizeof(dst[0])) - length) {
        //
        return;
    }
    // Copy float by float for shorter arrays.
    if (src == dst && srcPos < dstPos && dstPos < srcPos + length) {
        // Copy backward (to avoid overwriting elements before
        // they are copied in case of an overlap on the same
        // array.)
        for (int i = length - 1; i >= 0; --i) {
            dst[dstPos + i] = src[srcPos + i];
        }
    } else {
        // Copy forward.
        for (int i = 0; i < length; ++i) {
            dst[dstPos + i] = src[srcPos + i];
        }
    }
}

float utils::sTemp[32] = {0};

/**
 * Rotates matrix m in place by angle a (in degrees)
 * around the axis (x, y, z).
 *
 * @param m source matrix
 * @param mOffset index into m where the matrix starts
 * @param a angle to rotate in degrees
 * @param x X axis component
 * @param y Y axis component
 * @param z Z axis component
 */
void utils::rotateM(float m[], int mOffset,
                    float a, float x, float y, float z) {
    Mat4::setRotateM(sTemp, 0, a, x, y, z);
    Mat4::multiplyMM(sTemp, 16, m, mOffset, sTemp, 0);
    arraycopy(sTemp, 16, m, mOffset, 16);
}

int utils::loadTextureUseStb(void *pManager, const char *fileName) {
    int w, h, n;
    std::vector<uint8_t> vert_data;
    ReadFile(pManager, fileName, &vert_data);
    unsigned char *pixels = stbi_load_from_memory(vert_data.data(), vert_data.size(), &w, &h, &n,
                                                  4);
    unsigned textureId = 0;
    /**
    *   产生一个纹理Id,可以认为是纹理句柄，后面的操作将书用这个纹理id
    */
    glGenTextures(1, &textureId);
    //使用纹理单元
    glActiveTexture(GL_TEXTURE0);
    /**
    *   使用这个纹理id,或者叫绑定(关联)
    */
    glBindTexture(GL_TEXTURE_2D, textureId);
    /**
    *   指定纹理的放大,缩小滤波，使用线性方式，即当图片放大的时候插值方式
    */
//    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    //设置缩小过滤为使用纹理中坐标最接近的一个像素的颜色作为需要绘制的像素颜色
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    //设置放大过滤为使用纹理中坐标最接近的若干个颜色，通过加权平均算法得到需要绘制的像素颜色
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
    //设置环绕方向S，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
    //设置环绕方向T，截取纹理坐标到[1/2n,1-1/2n]。将导致永远不会与border融合
    glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

    /**
    *   将图片的rgb数据上传给opengl.
    */
    glTexImage2D(
            GL_TEXTURE_2D,      //! 指定是二维图片
            0,                  //! 指定为第一级别，纹理可以做mipmap,即lod,离近的就采用级别大的，远则使用较小的纹理
            GL_RGBA,            //! 纹理的使用的存储格式
            w,                  //! 宽度，老一点的显卡，不支持不规则的纹理，即宽度和高度不是2^n。
            h,                  //! 宽度，老一点的显卡，不支持不规则的纹理，即宽度和高度不是2^n。
            0,                  //! 是否的边
            GL_RGBA,            //! 数据的格式，bmp中，windows,操作系统中存储的数据是bgr格式
            GL_UNSIGNED_BYTE,   //! 数据是8bit数据
            pixels
    );
    return textureId;
}

unsigned int utils::loadTextureUseFreeImage(void *aAssetManager, const char *fileName) {
    unsigned textureId = 0;
#ifdef USE_FREEIMAGE
    FIBITMAP *dib = 0;
    std::vector<uint8_t> vert_data;
    ReadFile(aAssetManager, fileName, &vert_data);
    int size = vert_data.size();
    FIMEMORY *m_mem = FreeImage_OpenMemory(vert_data.data(), size);
    FREE_IMAGE_FORMAT fif = FreeImage_GetFileTypeFromMemory(m_mem, size);
    if (fif == FIF_UNKNOWN) {
        fif = FreeImage_GetFIFFromFilename(fileName);
    }
    if (fif == FIF_UNKNOWN) {
        FreeImage_CloseMemory(m_mem);
//        LOGE("FIF_UNKNOWN");
        return 0;
    }
    if (!FreeImage_FIFSupportsReading(fif)) {
        FreeImage_CloseMemory(m_mem);
//        LOGE("FreeImage_FIFSupportsReading: fif");
        return 0;
    }
    dib = FreeImage_LoadFromMemory(fif, m_mem, 0);
    if (!dib) {
        FreeImage_CloseMemory(m_mem);
//        LOGE("FreeImage_LoadFromMemory dib = 0");
        return 0;
    }

    //3 转化为rgb 24色
    dib = FreeImage_ConvertTo24Bits(dib);
    //4 获取数据指针
    uint8_t *pixels = FreeImage_GetBits(dib);
    int width = FreeImage_GetWidth(dib);
    int height = FreeImage_GetHeight(dib);
    for (int i = 0; i < width * height * 3; i += 3) {
        uint8_t temp = pixels[i];
        pixels[i] = pixels[i + 2];
        pixels[i + 2] = temp;
    }
    /**
    *   产生一个纹理Id,可以认为是纹理句柄，后面的操作将书用这个纹理id
    */
    glGenTextures(1, &textureId);
    //使用纹理单元
    glActiveTexture(GL_TEXTURE0);
    /**
    *   使用这个纹理id,或者叫绑定(关联)
    */
    glBindTexture(GL_TEXTURE_2D, textureId);
    /**
    *   指定纹理的放大,缩小滤波，使用线性方式，即当图片放大的时候插值方式
    */
    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

    glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
    /**
    *   将图片的rgb数据上传给opengl.
    */
    glTexImage2D(
            GL_TEXTURE_2D,      //! 指定是二维图片
            0,                  //! 指定为第一级别，纹理可以做mipmap,即lod,离近的就采用级别大的，远则使用较小的纹理
            GL_RGB,             //! 纹理的使用的存储格式
            width,              //! 宽度，老一点的显卡，不支持不规则的纹理，即宽度和高度不是2^n。
            height,             //! 宽度，老一点的显卡，不支持不规则的纹理，即宽度和高度不是2^n。
            0,                  //! 是否的边
            GL_RGB,             //! 数据的格式，bmp中，windows,操作系统中存储的数据是bgr格式
            GL_UNSIGNED_BYTE,   //! 数据是8bit数据
            pixels
    );
    /**
    *   释放内存
    */
    FreeImage_Unload(dib);
    FreeImage_CloseMemory(m_mem);
#endif
    return textureId;
}
