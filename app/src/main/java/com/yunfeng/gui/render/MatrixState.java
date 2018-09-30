package com.yunfeng.gui.render;

import android.opengl.Matrix;

import java.util.Arrays;
import java.util.Stack;

public class MatrixState {

    private static float[] mMVPMatrix = new float[16];// 获取具体物体的总变换矩阵
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

    private Stack<float[]> mStack = new Stack<>();      //变换矩阵堆栈

    //保护现场
    public void pushMatrix() {
        mStack.push(Arrays.copyOf(mMVPMatrix, 16));
    }

    //恢复现场
    public void popMatrix() {
        mMVPMatrix = mStack.pop();
    }

    public void clearStack() {
        mStack.clear();
    }

    //平移变换
    public static void translate(float x, float y, float z) {
        Matrix.translateM(mMVPMatrix, 0, x, y, z);
    }

    //旋转变换
    public void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(mMVPMatrix, 0, angle, x, y, z);
    }

    //缩放变换
    public void scale(float x, float y, float z) {
        Matrix.scaleM(mMVPMatrix, 0, x, y, z);
    }

    //设置相机
    public void setCamera(float ex, float ey, float ez, float cx, float cy, float cz, float ux, float uy, float uz) {
        Matrix.setLookAtM(mCameraMatrix, 0, ex, ey, ez, cx, cy, cz, ux, uy, uz);
    }

    public void frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    public void ortho(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mProjMatrix, 0, left, right, bottom, top, near, far);
    }

    public float[] getFinalMatrix() {
        float[] ans = new float[16];
        Matrix.multiplyMM(ans, 0, mCameraMatrix, 0, mMVPMatrix, 0);
        Matrix.multiplyMM(ans, 0, mProjMatrix, 0, ans, 0);
        return ans;
    }
}
