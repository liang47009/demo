package com.yunfeng.gui.render;

import android.content.Context;
import android.opengl.GLES20;

import com.yunfeng.gui.ui.IGeometry;
import com.yunfeng.gui.ui.Square;
import com.yunfeng.gui.ui.Triangle;

public class GLES2Renderer implements IRenderer {
    private Context mContext;
    private IGeometry mSquare;
    private IGeometry mTriangle;

    public GLES2Renderer(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean init() {
        GLES20.glClearColor(0, 0, 0.5f, 0);
        mSquare = new Square();
        mSquare.init(mContext);

        mTriangle = new Triangle();
        mTriangle.init(mContext);
        return true;
    }

    @Override
    public void sizeChanged(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / (float) height;

        MatrixState.frustumM(-ratio, ratio, -1f, 1f, 3f, 7f);

        mSquare.sizeChanged(width, height);
        mTriangle.sizeChanged(width, height);
    }

    @Override
    public void drawFrame() {
        MatrixState.setLookAtM(0, 0, 5f, 0, 0, 0, 0, 1f, 0);
        MatrixState.multiplyMM(0, 0, 0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mSquare.draw();
        mTriangle.draw();
    }

}
