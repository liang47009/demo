#include <GLES2/gl2.h>
#include "Renderer.h"
#include "MatrixStat.h"

#ifdef USE_FREEIMAGE
#include <freeimage/Source/FreeImage.h>
#endif

GLESRenderer::GLESRenderer() {
}

GLESRenderer::~GLESRenderer() {

}

bool GLESRenderer::init(AAssetManager *pManager) {
#ifdef USE_FREEIMAGE
    FreeImage_Initialise(true);
#endif
    m_square.init(pManager);
    m_triangle.init(pManager);
//    _shader.initialize();
    m_AAssetManager = pManager;
    glClearColor(0, 0, 0.5f, 0);
    //打开背面剪裁
    glEnable(GL_CULL_FACE);
    // 开启深度测试
    glEnable(GL_DEPTH_TEST);
    return true;
}

bool GLESRenderer::onChanged(int width, int height) {
    glViewport(0, 0, width, height);
    // 计算长和宽的比例，由于在OpenGLes坐标系里最大是1，具体的算法是:
    // x / width = 1 / height;
    // 所以 x = width /height;
    float ratio = (float) width / height;
    //设置透视投影
    Mat4::frustumM(MatrixStat::mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 20);
    //设置相机位置
    Mat4::setLookAtM(MatrixStat::mViewMatrix.Ptr(), 0, 5.0f, 5.0f, 10.0f, 0.0f, 0.0f, 0.0f, 0.0f,
                     1.0f, 0.0f);

    Mat4::multiplyMM(MatrixStat::mModleMatrix.Ptr(), 0, MatrixStat::mProjectionMatrix.Ptr(), 0,
                     MatrixStat::mViewMatrix.Ptr(), 0);

    m_triangle.changed(width, height);
    m_square.changed(width, height);
    return true;
}

void GLESRenderer::onDrawFrame() {
    glClearColor(0.5f, 0.5f, 0.5f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    m_square.draw();
    m_triangle.draw();
}

void GLESRenderer::update() {

}
