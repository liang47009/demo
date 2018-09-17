package com.yunfeng.gui.render;

import android.content.Context;
import android.opengl.GLES20;

import com.yunfeng.gui.ui.IGeometry;
import com.yunfeng.gui.ui.Square;
import com.yunfeng.gui.ui.Triangle;

import java.util.ArrayList;
import java.util.List;

public class GLES2Renderer implements IRenderer {
    private Context mContext;
//    private IGeometry mSquare;
//    private IGeometry mTriangle;

    private List<IGeometry> viewList = new ArrayList<>(16);

    public GLES2Renderer(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean init() {
//        mSquare = new Square();
//        mTriangle = new Triangle();
//        for (IGeometry geometry : viewList) {
//            geometry.init(mContext);
//        }

        return true;
    }

    @Override
    public void sizeChanged(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / (float) height;

        MatrixState.frustumM(-ratio, ratio, -1f, 1f, 3f, 7f);

        for (IGeometry geometry : viewList) {
            geometry.sizeChanged(width, height);
        }

//        mSquare.sizeChanged(width, height);
//        mTriangle.sizeChanged(width, height);
    }

    public void addView(IGeometry geometry, float x, float y) {
        geometry.init(mContext);
        geometry.setPosition(x, y, -1.0f);
        viewList.add(geometry);
    }

    //    Parameter	RGB Factor	Alpha Factor
//    GL_ZERO	(0, 0, 0)	0
//    GL_ONE	(1, 1, 1)	1
//    GL_SRC_COLOR	(Rs0, Gs0, Bs0)	As0
//    GL_ONE_MINUS_SRC_COLOR	(1, 1, 1) - (Rs0, Gs0, Bs0)	1 - As0
//    GL_DST_COLOR	(Rd, Gd, Bd)	Ad
//    GL_ONE_MINUS_DST_COLOR	(1, 1, 1) - (Rd, Gd, Bd)	1 - Ad
//    GL_SRC_ALPHA	(As0, As0, As0)	As0
//    GL_ONE_MINUS_SRC_ALPHA	(1, 1, 1) - (As0, As0, As0)	1 - As0
//    GL_DST_ALPHA	(Ad, Ad, Ad)	Ad
//    GL_ONE_MINUS_DST_ALPHA	(1, 1, 1) - (Ad, Ad, Ad)	Ad
//    GL_CONSTANT_COLOR	(Rc, Gc, Bc)	Ac
//    GL_ONE_MINUS_CONSTANT_COLOR	(1, 1, 1) - (Rc, Gc, Bc)	1 - Ac
//    GL_CONSTANT_ALPHA	(Ac, Ac, Ac)	Ac
//    GL_ONE_MINUS_CONSTANT_ALPHA	(1, 1, 1) - (Ac, Ac, Ac)	1 - Ac
//    GL_SRC_ALPHA_SATURATE	(i, i, i)	1
    @Override
    public void drawFrame() {
        MatrixState.setLookAtM(0, 0, 5f, 0, 0, 0, 0, 1f, 0);
        MatrixState.multiplyMM(0, 0, 0);

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.3f, 0.3f, 0.3f, 0.3f);

        GLES20.glEnable(GLES20.GL_BLEND);
        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        for (IGeometry geometry : viewList) {
            geometry.draw();
        }

//        mSquare.draw();
//        mTriangle.draw();

        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
    }

}
