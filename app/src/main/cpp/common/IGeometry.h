//
// Created by xll on 2018/10/9.
//

#ifndef RENDERER_IGEOMETRY_H
#define RENDERER_IGEOMETRY_H

class IGeometry {
public:
    virtual ~IGeometry() {};

    virtual bool init(void *pManager) = 0;

    virtual void draw() = 0;

    virtual void changed(int width, int height)=0;

protected:
    ProgramId *programId;
    Mat4 mTransformMatrix;
    float mAngle;

};


#endif //RENDERER_IGEOMETRY_H
