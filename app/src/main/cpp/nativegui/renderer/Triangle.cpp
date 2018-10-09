//
// Created by xll on 2018/9/14.
//

#include "Triangle.h"
#include "utils.h"
#include "MatrixStat.h"

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

void Triangle::draw() {
    mAngle += 0.1f;
    if (mAngle > 360) {
        mAngle = 0.0f;
    }
    Mat4::setRotateM(MatrixStat::mModleMatrix.Ptr(), 0, mAngle, 0, 0, -1.0f);
    Mat4::multiplyMM(mTransformMatrix.Ptr(), 0, MatrixStat::mModleMatrix.Ptr(), 0,
                     MatrixStat::mModleMatrix.Ptr(), 0);

    VertexTriangle vertexs[] = {0, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0};
    _shader.begin();
    glEnableVertexAttribArray(_shader._position);
    glVertexAttribPointer(_shader._position, 3, GL_FLOAT, false, 0, vertexs);
    glUniformMatrix4fv(_shader._mvp, 1, false, mTransformMatrix.Ptr());
    glUniform4f(_shader._color, 0, 0.5f, 0, 1.0f);
    glDrawArrays(GL_TRIANGLES, 0, 3);
    glDisableVertexAttribArray(_shader._position);
    _shader.end();
}

void Triangle::changed(int width, int height) {

}
