package com.yunfeng.gui.ui;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yunfeng.gui.render.IProgramId;
import com.yunfeng.gui.render.SquareProgramId;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle implements IGeometry {
    private float triangleCoords[] = {
            0.5f, 0.5f, 0.0f, // top
            -0.5f, -0.5f, 0.0f, // bottom left
            0.5f, -0.5f, 0.0f  // bottom right
    };
    private float color[] = {1.0f, 1.0f, 1.0f, 1.0f}; //白色

    private static final int COORDS_PER_VERTEX = 2;

    private FloatBuffer mSquareCoordsBuffer;

    private float[] mTransformMatrix = new float[16];

    private int mMatrixHandle = -1;
    private int mPositionHandle = -1;
    private int mColorHandle = -1;

    @Override
    public boolean init(Context context) {
        mSquareCoordsBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mSquareCoordsBuffer.put(triangleCoords).position(0);

        IProgramId shaderProgram = new SquareProgramId();
        shaderProgram.init(context);

        Matrix.setIdentityM(mTransformMatrix, 0);
        Matrix.rotateM(mTransformMatrix, 0, 90, 0, 0, 1.0f);

        mPositionHandle = shaderProgram.get(IProgramId.POSITION);
        mMatrixHandle = shaderProgram.get(IProgramId.MATRIX);
        mColorHandle = shaderProgram.get(IProgramId.COLOR);

        return true;
    }

    @Override
    public void draw(float[] mvpMatrix) {
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mSquareCoordsBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        Matrix.multiplyMM(mvpMatrix, 0, mvpMatrix, 0, mTransformMatrix, 0);
        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mvpMatrix, 0);
        //设置绘制三角形的颜色
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);
        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
    }

    @Override
    public void sizeChanged(int width, int height) {

    }
}
