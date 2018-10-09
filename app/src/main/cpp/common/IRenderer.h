#ifndef _IRENDERER_H_
#define _IRENDERER_H_

class IRenderer {
public:
    virtual bool init(AAssetManager *pManager) =0;

    virtual bool onChanged(int width, int height)=0;

    virtual void update()=0;

    virtual void onDrawFrame()=0;

};

#endif//_IRENDERER_H_