package com.yunfeng.gui.render;

import android.opengl.Matrix;

public class MatrixState {

    private static final float[] mMVPMatrix = new float[16];// 获取具体物体的总变换矩阵
    private static final float[] mProjMatrix = new float[16];// 4x4矩阵 存储投影矩阵
    private static final float[] mCameraMatrix = new float[16];// 摄像机位置朝向9参数矩阵

    public static void frustumM(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    public static void setLookAtM(float eyeX, float eyeY, float eyeZ, float centerX, float centerY, float centerZ, float upX, float upY, float upZ) {
        Matrix.setLookAtM(mCameraMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, upX, upY, upZ);
    }

    public static void multiplyMM(int resultOffset, int lhsOffset, int rhsOffset) {
        Matrix.multiplyMM(mMVPMatrix, resultOffset, mProjMatrix, lhsOffset, mCameraMatrix, rhsOffset);
    }

    public static float[] getMvpMatrix() {
        return mMVPMatrix;
    }

    public static float[] getProjMatix() {
        return mProjMatrix;
    }

    public static float[] getCameraMatrix() {
        return mCameraMatrix;
    }

    public static void multiplyMM(float[] mTransformMatrix) {
        Matrix.multiplyMM(mTransformMatrix, 0, mProjMatrix, 0, mCameraMatrix, 0);
    }

    public static void rotateM(float angle, float x, float y, float z) {
        Matrix.rotateM(mCameraMatrix, 0, angle, x, y, z);
    }
}
