//
// Created by xll on 2018/9/14.
//
#include "Square.h"
#include "utils.h"
#include "MatrixStat.h"

Square::Square() {
    _textureId = -1;
    vPosition = -1;
    vTexCoord = -1;
    uMatrix = -1;
    fTexture = -1;
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
    programId = new PROGRAM_TR_TEX();
}

Square::~Square() {
    delete programId;
    delete m_square_vertxs;
    delete m_texture_coords;
}

bool Square::init(void *pManager) {
    programId->initialize();
#ifdef USE_FREEIMAGE
    _textureId = utils::loadTextureUseFreeImage(pManager, "models/andy.png");
#else
    _textureId = utils::loadTextureUseStb(pManager, "models/andy.png");
#endif
    vPosition = programId->getShaderHandler("vPosition");
    vTexCoord = programId->getShaderHandler("vCoordinate");
    uMatrix = programId->getShaderHandler("vMatrix");
    fTexture = programId->getShaderHandler("vTexture");
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
    programId->begin();
    glEnableVertexAttribArray(vPosition);
    glEnableVertexAttribArray(vTexCoord);

    //传入顶点坐标
    glVertexAttribPointer(vPosition, 2, GL_FLOAT, false, 0, m_square_vertxs);
    //传入纹理坐标
    glVertexAttribPointer(vTexCoord, 2, GL_FLOAT, false, 0, m_texture_coords);
    glUniformMatrix4fv(uMatrix, 1, false, mTransformMatrix.Ptr());
    glUniform1i(fTexture, 0);
    glDrawArrays(GL_TRIANGLE_STRIP, 0, 4);

    glDisableVertexAttribArray(vPosition);
    glDisableVertexAttribArray(vTexCoord);
    programId->end();
}

void Square::changed(int width, int height) {

}
