package com.yunfeng.gui.render;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yunfeng.gui.ui.Square;

public class GLES2Renderer implements IRenderer {
    private Context mContext;
    private Square mSquare;

    private float[] mMVPMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mCameraMatrix = new float[16];

    public GLES2Renderer(Context context) {
        this.mContext = context;
    }

    @Override
    public boolean init() {
        GLES20.glClearColor(0, 0, 0.5f, 0);
        mSquare = new Square(mContext);
        return true;
    }

    @Override
    public void sizeChanged(int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        float ratio = (float) width / (float) height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f);
    }

    @Override
    public void drawFrame() {
        Matrix.setLookAtM(mCameraMatrix, 0, 0, 0, 5f, 0, 0, 0, 0, 1f, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mCameraMatrix, 0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mSquare.draw(mMVPMatrix);
    }

}
