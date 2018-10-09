//
// Created by xll on 2018/9/14.
//
#include "Square.h"
#include "utils.h"
#include "MatrixStat.h"

Square::Square() {
    _textureId = -1;
    mAngle = 0.0f;
    m_square_vertxs = new float[8]{
            -1.0f, 1.0f,    //左上角
            -1.0f, -1.0f,   //左下角
            1.0f, 1.0f,     //右上角
            1.0f, -1.0f     //右下角
    };

    m_texture_coords = new float[8]{
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 0.0f,
            1.0f, 1.0f,
    };
}

Square::~Square() {
}

bool Square::init(AAssetManager *pManager) {
    _shader.initialize();
#ifdef USE_FREEIMAGE
    _textureId = utils::loadTextureUseFreeImage(pManager, "models/andy.png");
#else
    _textureId = utils::loadTextureUseStb(pManager, "models/andy.png");
#endif
    return true;
}

void Square::draw() {
    mAngle += 0.1f;
    if (mAngle > 360) {
        mAngle = 0.0f;
    }
    Mat4::setRotateM(MatrixStat::mModleMatrix.Ptr(), 0, mAngle, -1.0f, 0, 0);
    Mat4::multiplyMM(mTransformMatrix.Ptr(), 0, MatrixStat::mModleMatrix.Ptr(), 0,
                     MatrixStat::mModleMatrix.Ptr(), 0);
    _shader.begin();
//    glVertexAttribPointer(_shader.vPosition, 3, GL_FLOAT, false, 0, m_square_coords);
//    glVertexAttribPointer(_shader.vTexCoord, 2, GL_FLOAT, false, 0, m_texture_coords);
//    glUniform1i(_shader.fTexture, 0);
//    glDrawElements(GL_TRIANGLES, 12, GL_UNSIGNED_SHORT, m_index_coords);
    //传入顶点坐标
    glVertexAttribPointer(_shader.vPosition, 2, GL_FLOAT, false, 0, m_square_vertxs);
    //传入纹理坐标
    glVertexAttribPointer(_shader.vTexCoord, 2, GL_FLOAT, false, 0, m_texture_coords);
    glUniformMatrix4fv(_shader.uMatrix, 1, false, mTransformMatrix.Ptr());
    glUniform1i(_shader.fTexture, 0);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);
    _shader.end();
}

void Square::changed(int width, int height) {

}
