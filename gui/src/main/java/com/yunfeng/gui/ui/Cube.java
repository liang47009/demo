package com.yunfeng.gui.ui;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yunfeng.gui.helper.TextureHelper;
import com.yunfeng.gui.render.IProgramId;
import com.yunfeng.gui.render.MatrixState;
import com.yunfeng.gui.render.ProgramIdManager;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

public class Cube extends View {

    private final float cubePositions[] = {//
            -1.0f, 1.0f, 1.0f,    //正面左上0
            0, 1,//
            -1.0f, -1.0f, 1.0f,   //正面左下1
            0, 0,//
            1.0f, -1.0f, 1.0f,    //正面右下2
            1, 0,//
            1.0f, 1.0f, 1.0f,     //正面右上3
            1, 0,//
            -1.0f, 1.0f, -1.0f,    //反面左上4
            0, 1,//
            -1.0f, -1.0f, -1.0f,   //反面左下5
            0, 1,//
            1.0f, -1.0f, -1.0f,    //反面右下6
            1, 0,//
            1.0f, 1.0f, -1.0f,     //反面右上7
            1, 1,//
    };

    private final short index[] = {//
            6, 7, 4, 6, 4, 5,    //后面
            6, 3, 7, 6, 2, 3,    //右面
            6, 5, 1, 6, 1, 2,    //下面
            0, 3, 2, 0, 2, 1,    //正面
            0, 1, 5, 0, 5, 4,    //左面
            0, 7, 3, 0, 4, 7,    //上面
    };

    private final float color[] = {
//            0f, 1f, 0f, 1f, //
//            0f, 1f, 0f, 1f, //
//            0f, 1f, 0f, 1f, //
//            0f, 1f, 0f, 1f, //
//            1f, 0f, 0f, 1f, //
//            1f, 0f, 0f, 1f, //
//            1f, 0f, 0f, 1f, //
//            1f, 0f, 0f, 1f,
    };

    private static final int COORDS_PER_VERTEX = 3;

    private FloatBuffer mCubeCoordsBuffer;
    private FloatBuffer mColorBuffer;
    private ShortBuffer mIndexBuffer;

    private int mMatrixHandle = -1;
    private int mPositionHandle = -1;
    private int mTextureVertextHandle = -1;
    private int mColorVertextHandle = -1;

    @Override
    public boolean init(Context context) {
        mCubeCoordsBuffer = ByteBuffer.allocateDirect(cubePositions.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mCubeCoordsBuffer.put(cubePositions).position(0);

        mColorBuffer = ByteBuffer.allocateDirect(color.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mColorBuffer.put(color).position(0);

        mIndexBuffer = ByteBuffer.allocateDirect(index.length * 2).order(ByteOrder.nativeOrder()).asShortBuffer();
        mIndexBuffer.put(index).position(0);

        shaderProgram = ProgramIdManager.getInstance().getProgrameId(ProgramIdManager.SQUARE_PROGRAM);
        shaderProgram.init(context);

        textureId = TextureHelper.loadTexture(context, "andy", "drawable");

        mPositionHandle = shaderProgram.get(IProgramId.POSITION);
        mMatrixHandle = shaderProgram.get(IProgramId.MATRIX);
        mTextureVertextHandle = shaderProgram.get(IProgramId.TEXTURE);
        mColorVertextHandle = shaderProgram.get(IProgramId.COLOR);

        return true;
    }

    private float mAngle = 0.1f;
    private int textureId = -1;

    @Override
    protected void drawFrame() {

        if (mAngle > 360) {
            mAngle = 0.1f;
        } else {
            mAngle += 0.1f;
        }

        Matrix.setRotateM(mTransformMatrix, 0, mAngle, -0.1f, -0.1f, -0.1f);
        Matrix.multiplyMM(scratch, 0, MatrixState.getMvpMatrix(), 0, mTransformMatrix, 0);
        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, scratch, 0);

        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glEnableVertexAttribArray(mTextureVertextHandle);

        // 3(x, y, z)
        //(2+3)*4(float size) = 20
        mCubeCoordsBuffer.position(0);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 20, mCubeCoordsBuffer);

        mCubeCoordsBuffer.position(3);
        GLES20.glVertexAttribPointer(mTextureVertextHandle, 2, GLES20.GL_FLOAT, false, 20, mCubeCoordsBuffer);

        //索引法绘制正方体
        GLES20.glDrawElements(GLES20.GL_TRIANGLES, index.length, GLES20.GL_UNSIGNED_SHORT, mIndexBuffer);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureVertextHandle);
    }

    @Override
    public void sizeChanged(int width, int height) {

    }

    @Override
    public void setPosition(float x, float y, float z) {

    }

}

