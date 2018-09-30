//
// Created by xll on 2018/9/14.
//

#include "Triangle.h"
#include "utils.h"

Triangle::Triangle() {
    _textureId = -1;
    mAngle = 0.0f;
}

Triangle::~Triangle() {

}

bool Triangle::init(AAssetManager *pManager) {
    _shader.initialize();
    _textureId = utils::loadTextureUseStb(pManager, "models/andy.png");
    return true;
}

void Triangle::draw(Mat4 &mat_model_) {
    mAngle += 0.1f;
    if (mAngle > 360) {
        mAngle = 0.0f;
    }
    // 设置相机矩阵
    Mat4::setLookAtM(mViewMatrix.Ptr(), 0, 0, 0, -3, 0, 0, 0, 0, 1.0f, 0);
    // 透视投影矩阵与相机矩阵相乘，得到最后的矩阵mMVPMatrix
    Mat4::multiplyMM(mat_model_.Ptr(), 0, mProjectionMatrix.Ptr(), 0, mViewMatrix.Ptr(), 0);
    Mat4::setRotateM(mRotationMatrix.Ptr(), 0, mAngle, 0, 0, -1.0f);
    Mat4 scratch;
    Mat4::multiplyMM(scratch.Ptr(), 0, mat_model_.Ptr(), 0, mRotationMatrix.Ptr(), 0);

    VertexTriangle vertexs[] = {0, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0};
    _shader.begin();
    glEnableVertexAttribArray(_shader._position);
    glVertexAttribPointer(_shader._position, 3, GL_FLOAT, false, 0, vertexs);
    glUniformMatrix4fv(_shader._mvp, 1, false, scratch.Ptr());
    glUniform4f(_shader._color, 0, 0.5f, 0, 1.0f);
    glDrawArrays(GL_TRIANGLES, 0, 3);
    _shader.end();
}

void Triangle::changed(int width, int height) {
    glViewport(0, 0, width, height);

    // 计算长和宽的比例，由于在OpenGLes坐标系里最大是1，具体的算法是:
    // x / width = 1 / height;
    // 所以 x = width /height;
    float ratio = (float) width / height;
    Mat4::frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);
}
