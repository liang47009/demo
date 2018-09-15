#include <GLES2/gl2.h>
#include <freeimage.h>
#include "Renderer.h"

Renderer::Renderer() {
}

Renderer::~Renderer() {

}

bool Renderer::init(AAssetManager *pManager) {
    FreeImage_Initialise(true);
    m_square.init(pManager);
    m_triangle.init();
//    _shader.initialize();
    m_AAssetManager = pManager;
    glClearColor(0, 0, 0.5f, 0);
    return true;
}

bool Renderer::onChanged(int width, int height) {
    glViewport(0, 0, width, height);
    // 计算长和宽的比例，由于在OpenGLes坐标系里最大是1，具体的算法是:
    // x / width = 1 / height;
    // 所以 x = width /height;
    float ratio = (float) width / height;
    Mat4::frustumM(mat_projection_, 0, -ratio, ratio, -1, 1, 3, 7);

    m_triangle.changed(width, height);
    return true;
}

void Renderer::onDrawFrame() {
    glClearColor(0.5f, 0.5f, 0.5f, 1.f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    Mat4::setLookAtM(mat_view_.Ptr(), 0, 0, 0, 5.0f, 0, 0, 0, 0, 1.0f, 0);
    Mat4::multiplyMM(mat_model_.Ptr(), 0, mat_projection_.Ptr(), 0, mat_view_.Ptr(), 0);

    m_square.draw(mat_model_);
//    m_triangle.draw(mat_model_);
}

void Renderer::update() {

}
