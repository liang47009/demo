package com.yunfeng.gui.render;

import android.content.Context;
import android.opengl.GLES20;

import com.yunfeng.gui.helper.CoordersHelper;
import com.yunfeng.gui.ui.ColorCube;
import com.yunfeng.gui.ui.IGeometry;

import java.util.ArrayList;
import java.util.List;

public class GLES2Renderer implements IRenderer {
    private Context mContext;

    private List<IGeometry> viewList = new ArrayList<>(16);

    public GLES2Renderer(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean init() {
        //设置屏幕背景色RGBA
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
//        //打开背面剪裁
        GLES20.glEnable(GLES20.GL_CULL_FACE);
        ColorCube cube = new ColorCube();
        addView(cube, 100, 100);
        return true;
    }

    @Override
    public void sizeChanged(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / (float) height;
        CoordersHelper.ratio = ratio;
        CoordersHelper.screenWidth = width;
        CoordersHelper.screenHeight = height;
        // 计算长和宽的比例，由于在OpenGLes坐标系里最大是1，具体的算法是:
        // x / width = 1 / height;
        // 所以 x = width /height;
//        MatrixState.frustumM(-ratio, ratio, -1f, 1f, 3f, 7f);
        //设置透视投影
        MatrixState.frustumM(-ratio, ratio, -1, 1, 3, 20);
        //设置相机位置
        MatrixState.setLookAtM(5.0f, 5.0f, -10.0f, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        MatrixState.multiplyMM(0, 0, 0);

        for (IGeometry geometry : viewList) {
            geometry.sizeChanged(width, height);
        }

    }

    public void addView(IGeometry geometry, float x, float y) {
        geometry.init(mContext);

        float tempX = CoordersHelper.toGLX(x);
        float tempY = CoordersHelper.toGLY(y);

        geometry.setPosition(tempX, tempY, -1.0f);
        viewList.add(geometry);
    }

    @Override
    public void onTouch(float x, float y) {
        float glx = CoordersHelper.toGLX(x);
        float gly = CoordersHelper.toGLY(y);

        MatrixState.translate(glx, gly, 0);
    }

    @Override
    public void drawFrame() {
        // 开启深度测试
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
        GLES20.glClearColor(0.3f, 0.3f, 0.3f, 0.3f);

//        GLES20.glEnable(GLES20.GL_BLEND);
//        GLES20.glBlendFunc(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);

        for (IGeometry geometry : viewList) {
            geometry.draw();
        }

//        GLES20.glDisable(GLES20.GL_BLEND);
        GLES20.glDisable(GLES20.GL_DEPTH_TEST);
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
}
