package com.yunfeng.gui.ui;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yunfeng.gui.render.IProgramId;
import com.yunfeng.gui.render.MatrixState;
import com.yunfeng.gui.render.ProgramIdManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Triangle extends View {
    private float triangleCoords[] = {0.5f, 0.5f, 0.0f, 1.0f,// top
            -0.5f, -0.5f, 0.0f, 1.0f,// bottom left
            0.5f, -0.5f, 0.0f, 1.0f // bottom right
    };
    //设置颜色
    private float color[] = {0.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f};

    private static final int COORDS_PER_VERTEX = 4;

    private FloatBuffer mSquareCoordsBuffer;
    private FloatBuffer mColorBuffer;

    private int mMatrixHandle = -1;
    private int mPositionHandle = -1;
    private int mColorHandle = -1;

    @Override
    public boolean init(Context context) {
        mSquareCoordsBuffer = ByteBuffer.allocateDirect(triangleCoords.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mSquareCoordsBuffer.put(triangleCoords).position(0);

        mColorBuffer = ByteBuffer.allocateDirect(color.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mColorBuffer.put(color).position(0);

        shaderProgram = ProgramIdManager.getInstance().getProgrameId(ProgramIdManager.TRIANGLE_PROGRAM);
        shaderProgram.init(context);

        mPositionHandle = shaderProgram.get(IProgramId.POSITION);
        mMatrixHandle = shaderProgram.get(IProgramId.MATRIX);
        mColorHandle = shaderProgram.get(IProgramId.COLOR);

        return true;
    }

    private float mAngle = 0.1f;

    @Override
    protected void drawFrame() {
        if (mAngle > 360) {
            mAngle = 0.1f;
        } else {
            mAngle += 0.1f;
        }

        Matrix.setRotateM(mTransformMatrix, 0, mAngle, -0.1f, -0.1f, -0.1f);
        Matrix.translateM(mTransformMatrix, 0, 0, 3, 0);
        Matrix.multiplyMM(scratch, 0, MatrixState.getMvpMatrix(), 0, mTransformMatrix, 0);

        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 16, mSquareCoordsBuffer);

        //设置绘制三角形的颜色
        GLES20.glEnableVertexAttribArray(mColorHandle);
        GLES20.glVertexAttribPointer(mColorHandle, 3, GLES20.GL_FLOAT, false, 16, mColorBuffer);

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, scratch, 0);
        //设置绘制三角形的颜色
//        GLES20.glUniform4fv(mColorHandle, 3, color, 0);

        //绘制三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorHandle);
    }

    @Override
    public void sizeChanged(int width, int height) {

    }

    @Override
    public void setPosition(float x, float y, float z) {
//        this.mPosition.set(x, y, z);
//        mSquareCoordsBuffer.put(0, x);
//        mSquareCoordsBuffer.put(1, y);
//        mSquareCoordsBuffer.put(2, z);
    }
}
