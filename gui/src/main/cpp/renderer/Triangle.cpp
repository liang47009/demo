//
// Created by xll on 2018/9/14.
//

#include "Triangle.h"

struct Vertex {
    float x, y, z;
//        float u, v;
//        float r, g, b, a;
};

Triangle::Triangle() {
    _textureId = -1;
}

Triangle::~Triangle() {

}

bool Triangle::init() {
    _shader.initialize();

    return true;
}

void Triangle::render() {
    Vertex vertexs[] = {0, 0.5f, 0, -0.5f, -0.5f, 0, 0.5f, -0.5f, 0};
    int per = sizeof(vertexs) / 3;
    _shader.begin();
    glEnableVertexAttribArray(_shader._position);
    glVertexAttribPointer(_shader._position, 3, GL_FLOAT, false, 0, vertexs);
    glUniform4f(_shader._color, 0, 0.5f, 0, 1.0f);
    glDrawArrays(GL_TRIANGLES, 0, 3);
    _shader.end();
}
