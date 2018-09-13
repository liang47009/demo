#include <GLES2/gl2.h>
#include <fstream>
#include <freeimage.h>
#include "Renderer.h"

bool
ReadFile(AAssetManager *aAssetManager, const char *fileName, std::vector<uint8_t> *buffer_ref) {
    std::ifstream f(fileName, std::ios::binary);
    if (f) {
        LOGI("reading:%s", fileName);
        f.seekg(0, std::ifstream::end);
        std::fpos<mbstate_t> fileSize = f.tellg();
        f.seekg(0, std::ifstream::beg);
        buffer_ref->reserve(fileSize);
        buffer_ref->assign(std::istreambuf_iterator<char>(f), std::istreambuf_iterator<char>());
        f.close();
        return true;
    } else {
        if (NULL == aAssetManager) {
            LOGE("AssetManager is null!");
            return false;
        }
        //Fallback to assetManager
        AAsset *assetFile = AAssetManager_open(aAssetManager, fileName, AASSET_MODE_BUFFER);
        if (!assetFile) {
            LOGE("assetFile is null: %s", fileName);
            return false;
        }
        uint8_t *data = (uint8_t *) AAsset_getBuffer(assetFile);
        int32_t size = AAsset_getLength(assetFile);
        if (data == NULL) {
            AAsset_close(assetFile);
            LOGE("Failed to load:%s", fileName);
            return false;
        }
        buffer_ref->reserve(size);
        buffer_ref->assign(data, data + size);
        AAsset_close(assetFile);
        return true;
    }
}

unsigned int loadTexture(AAssetManager *aAssetManager, const char *fileName) {
    unsigned textureId = 0;
    FIBITMAP *dib = 0;
    std::vector<uint8_t> vert_data;
    ReadFile(aAssetManager, fileName, &vert_data);
    int size = vert_data.size();
    LOGE("image size=%d", size);
    FIMEMORY *m_mem = FreeImage_OpenMemory(vert_data.data(), size);
    LOGE("FreeImage_OpenMemory m_mem=%p", m_mem);
    FREE_IMAGE_FORMAT fif = FreeImage_GetFileTypeFromMemory(m_mem, size);
    LOGE("FreeImage_OpenMemory fif=%p", &fif);
//    if (fif == FIF_UNKNOWN) {
//        fif = FreeImage_GetFIFFromFilename(fileName);
//    }
//    if (fif == FIF_UNKNOWN) {
//        FreeImage_CloseMemory(m_mem);
//        LOGE("FIF_UNKNOWN");
//        return 0;
//    }
//    if (!FreeImage_FIFSupportsReading(fif)) {
//        FreeImage_CloseMemory(m_mem);
//        LOGE("FreeImage_FIFSupportsReading: fif");
//        return 0;
//    }
    dib = FreeImage_LoadFromMemory(fif, m_mem, 0);
    if (!dib) {
        FreeImage_CloseMemory(m_mem);
        LOGE("FreeImage_LoadFromMemory dib = 0");
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
    LOGE("textureId = %d", textureId);
    return textureId;
}

Renderer::Renderer() {
}

Renderer::~Renderer() {

}

bool Renderer::init(AAssetManager *pManager) {
    m_AAssetManager = pManager;
    return true;
}

bool Renderer::onChanged(int width, int height) {
    // Initialize GL state.
    glEnable(GL_CULL_FACE);
    glEnable(GL_DEPTH_TEST);
    glDepthFunc(GL_LEQUAL);

    glViewport(0, 0, width, height);

    int32_t viewport[4];
    glGetIntegerv(GL_VIEWPORT, viewport);
    float fAspect = (float) viewport[2] / (float) viewport[3];

    const float CAM_NEAR = 5.f;
    const float CAM_FAR = 10000.f;
    mat_projection_ = Mat4::Perspective(fAspect, 1.f, CAM_NEAR, CAM_FAR);
    _textureId = loadTexture(m_AAssetManager, "models/andy.png");
    return true;
}

void Renderer::onDrawFrame() {
    glClearColor(0.5f, 0.5f, 0.5f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

    update();

    struct Vertex {
        float x, y, z;
        float u, v;
        float r, g, b, a;
    };

    //设置摄像机位置参数矩阵:摄像机位置向量,观察点位置向量,摄像机顶端指向向量
    Vertex vertexs[] =
            {
                    {-1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                    //{ 1.0f, -1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f },
                    //{ 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f },

                    //{ -1.0f, -1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f },
                    //{ 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f },
                    //{ -1.0f, 1.0f, 1.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f },
            };

    float gSize = 100;
    float gPos = -5;
    float rept = 100;

    //顺时针方向环绕
    Vertex grounds[] =
            {
                    {-gSize, gPos, -gSize, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                    {gSize,  gPos, -gSize, rept, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                    {gSize,  gPos, gSize,  rept, rept, 1.0f, 1.0f, 1.0f, 1.0f},

                    {-gSize, gPos, -gSize, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f, 1.0f},
                    {gSize,  gPos, gSize,  rept, rept, 1.0f, 1.0f, 1.0f, 1.0f},
                    {-gSize, gPos, gSize,  0.0f, rept, 1.0f, 1.0f, 1.0f, 1.0f},
            };

    _shader.begin();
    {
        Mat4 matWorld;
        //摄像机投影矩阵*摄像机位置矩阵*物体变换矩阵
        Mat4 MVP = mat_projection_ * mat_view_ * matWorld;
        glUniform1i(_shader._texture, 0);
        //绘制地面
        glBindTexture(GL_TEXTURE_2D, _textureId);
        glUniformMatrix4fv(_shader._MVP, 1, false, MVP.Ptr());

        //sizeof(Vertex)=36 sizeof(grounds)=216
        glVertexAttribPointer(_shader._positionAttr, 3, GL_FLOAT, false, sizeof(Vertex),
                              &grounds[0].x);
        glVertexAttribPointer(_shader._uvAttr, 2, GL_FLOAT, false, sizeof(Vertex), &grounds[0].u);
        glVertexAttribPointer(_shader._colorAttr, 4, GL_FLOAT, false, sizeof(Vertex),
                              &grounds[0].r);
        //sizeof(grounds)/sizeof(grounds[0])=6
        glDrawArrays(GL_TRIANGLES, 0, sizeof(grounds) / sizeof(grounds[0]));
    }
    _shader.end();

}

void Renderer::update() {
    const float CAM_X = 0.f;
    const float CAM_Y = 0.f;
    const float CAM_Z = 700.f;

    Mat4 mat_view_ = Mat4::LookAt(Vec3(CAM_X, CAM_Y, CAM_Z),
                                  Vec3(0.f, 0.f, 0.f),
                                  Vec3(0.f, 1.f, 0.f));
    mat_view_ = mat_view_ * mat_model_;
    //
    // Feed Projection and Model View matrices to the shaders
    Mat4 mat_vp = mat_projection_ * mat_view_;
}
