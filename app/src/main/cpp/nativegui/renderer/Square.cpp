//
// Created by xll on 2018/9/14.
//

#include "Square.h"
#include "utils.h"
#include "MatrixStat.h"

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
    return true;
}

void Square::draw() {
    _shader.begin();

    glEnableVertexAttribArray(_shader._position);
    glEnableVertexAttribArray(_shader._color);

    glActiveTexture(_textureId);
    glVertexAttribPointer(_shader._position, 2, GL_FLOAT, false, 0, m_square_coords);
    glVertexAttribPointer(_shader._color, 2, GL_FLOAT, false, 0, m_texture_coords);

    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

    glDisableVertexAttribArray(_shader._position);
    glDisableVertexAttribArray(_shader._color);
    _shader.end();
}

void Square::changed(int width, int height) {

}
