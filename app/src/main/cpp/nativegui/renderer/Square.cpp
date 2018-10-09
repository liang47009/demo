//
// Created by xll on 2018/9/14.
//
#include "Square.h"
#include "utils.h"

Square::Square() {
    m_square_coords = new float[15]{
            .0f, .0f, .0f,
            1.0f, 0.75f, .0f,
            -1.0f, 0.75f, 0.0f,
            -1.0f, -0.75f, 0.0f,
            1.0f, -0.75f, 0.0f
    };

    m_texture_coords = new float[10]{
            0.5f, 0.5f,
            1.0f, 0.0f,
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f
    };
    m_index_coords = new short[12]{
            0, 1, 2,
            0, 2, 3,
            0, 3, 4,
            0, 4, 1,
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
    _shader.begin();
    glVertexAttribPointer(_shader.vPosition, 3, GL_FLOAT, false, 0, m_square_coords);
    glVertexAttribPointer(_shader.vTexCoord, 2, GL_FLOAT, false, 0, m_texture_coords);
    glUniform1i(_shader.fTexture, 0);
    glDrawElements(GL_TRIANGLES, 12, GL_UNSIGNED_SHORT, m_index_coords);
    _shader.end();
}

void Square::changed(int width, int height) {

}
