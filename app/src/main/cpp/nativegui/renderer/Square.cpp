//
// Created by xll on 2018/9/14.
//

#include "Square.h"
#include "utils.h"

Square::Square() {
    m_square_coords = new float[8]{
            -0.5f, -0.5f,
            0.5f, -0.5f,
            -0.5f, 0.5f,
            0.5f, 0.5f};

    m_texture_coords = new float[8]{
            0, 1,
            1, 1,
            0, 0,
            1, 0
    };
}

Square::~Square() {
}

bool Square::init(AAssetManager *pManager) {
    _shader.initialize();
    _textureId = utils::loadTextureUseStb(pManager, "models/andy.png");

    Mat4::setIdentityM(mTransformMatrix.Ptr(), 0);
    utils::rotateM(mTransformMatrix.Ptr(), 0, 90, 0, 0, 1.0f);
    return true;
}

void Square::draw(Mat4 &mvpMatrix) {
    _shader.begin();

    glVertexAttribPointer(_shader._position, 2, GL_FLOAT, false, 0, m_square_coords);
    Mat4::multiplyMM(mvpMatrix.Ptr(), 0, mvpMatrix.Ptr(), 0, mTransformMatrix.Ptr(), 0);
    glUniformMatrix4fv(_shader._MVP, 1, false, mvpMatrix.Ptr());

    glEnableVertexAttribArray(_shader._uv);
    glVertexAttribPointer(_shader._uv, 2, GL_FLOAT, false, 0, m_texture_coords);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

    glDisableVertexAttribArray(_shader._position);
    glDisableVertexAttribArray(_shader._uv);
    _shader.end();
}
