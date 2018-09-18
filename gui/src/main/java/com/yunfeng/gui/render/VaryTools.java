package com.yunfeng.gui.render;

import android.opengl.Matrix;

import java.util.Arrays;
import java.util.Stack;

/**
 * tool
 * Created by xll on 2018/9/18.
 */
public class VaryTools {

    private static float[] mMatrixCamera = new float[16];    //相机矩阵
    private static float[] mMatrixProjection = new float[16];    //投影矩阵
    private static float[] mMatrixCurrent = {1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1}; //原始矩阵

    private static Stack<float[]> mStack = new Stack<>();     //变换矩阵堆栈

    //保护现场
    public static void pushMatrix() {
        mStack.push(Arrays.copyOf(mMatrixCurrent, 16));
    }

    //恢复现场
    public static void popMatrix() {
        mMatrixCurrent = mStack.pop();
    }

    public static void clearStack() {
        mStack.clear();
    }

    //平移变换
    public static void translate(float x, float y, float z) {
        Matrix.translateM(mMatrixCurrent, 0, x, y, z);
    }

    //旋转变换
    public static void rotate(float angle, float x, float y, float z) {
        Matrix.rotateM(mMatrixCurrent, 0, angle, x, y, z);
    }

    //缩放变换
    public static void scale(float x, float y, float z) {
        Matrix.scaleM(mMatrixCurrent, 0, x, y, z);
    }

    //设置相机
    public static void setCamera(float ex, float ey, float ez, float cx, float cy, float cz, float ux, float uy, float uz) {
        Matrix.setLookAtM(mMatrixCamera, 0, ex, ey, ez, cx, cy, cz, ux, uy, uz);
    }

    public static void frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix.frustumM(mMatrixProjection, 0, left, right, bottom, top, near, far);
    }

    public static void ortho(float left, float right, float bottom, float top, float near, float far) {
        Matrix.orthoM(mMatrixProjection, 0, left, right, bottom, top, near, far);
    }

    public static float[] getFinalMatrix() {
        float[] ans = new float[16];
        Matrix.multiplyMM(ans, 0, mMatrixCamera, 0, mMatrixCurrent, 0);
        Matrix.multiplyMM(ans, 0, mMatrixProjection, 0, ans, 0);
        return ans;
    }

}
