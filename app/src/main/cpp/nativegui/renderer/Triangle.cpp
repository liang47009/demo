//
// Created by xll on 2018/9/14.
//

#include "Triangle.h"
#include "utils.h"
#include "MatrixStat.h"

Triangle::Triangle() {
    _textureId = -1;
    mAngle = 0.0f;
    _position = -1;
    _color = -1;
    _mvp = -1;
    m_tri_vertxs = new float[9]{
            0, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0.0f,
    };
}

Triangle::~Triangle() {
    delete programId;
    delete m_tri_vertxs;
}

bool Triangle::init(void *pManager) {
    programId = new PROGRAM_Tr_U1();
    programId->initialize();

    _position = programId->getShaderHandler("_position");
    _color = programId->getShaderHandler("_color");
    _mvp = programId->getShaderHandler("_mvp");

    _textureId = utils::loadTextureUseStb(pManager, "models/andy.png");

    return true;
}

void Triangle::draw() {
    mAngle += 0.1f;
    if (mAngle > 360) {
        mAngle = 0.0f;
    }
    Mat4::setRotateM(MatrixStat::mModleMatrix.Ptr(), 0, mAngle, 0.1f, 0.1f, 0.1f);
    Mat4::multiplyMM(mTransformMatrix.Ptr(), 0, MatrixStat::mModleMatrix.Ptr(), 0,
                     MatrixStat::mModleMatrix.Ptr(), 0);
    programId->begin();
    glEnableVertexAttribArray(_position);

    glVertexAttribPointer(_position, 3, GL_FLOAT, false, 0, m_tri_vertxs);
    glUniformMatrix4fv(_mvp, 1, false, mTransformMatrix.Ptr());
    glUniform4f(_color, 0, 0.5f, 0, 1.0f);
    glDrawArrays(GL_TRIANGLES, 0, 3);

    glDisableVertexAttribArray(_position);
    programId->end();
}

void Triangle::changed(int width, int height) {

}
