package com.yunfeng.gui.ui;

import android.content.Context;
import android.opengl.GLES20;

import com.yunfeng.gui.helper.TextureHelper;
import com.yunfeng.gui.render.IProgramId;
import com.yunfeng.gui.render.MatrixState;
import com.yunfeng.gui.render.SquareProgramId;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Square extends View {
    private Context mContext;

    // 正方形的顶点坐标
    private float[] SQUARE_COORDS = {-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f};

    // 纹理的顶点坐标
    private float[] TEXTURE_COORDS = {0, 1, 1, 1, 0, 0, 1, 0};

    private static final int COORDS_PER_VERTEX = 2;

    private FloatBuffer mSquareCoordsBuffer;
    private FloatBuffer mTextureCoordsBuffer;

    private int mMatrixHandle = -1;
    private int mPositionHandle = -1;
    private int mTextureVertextHandle = -1;

    @Override
    public boolean init(Context context) {
        mContext = context;

        mSquareCoordsBuffer = ByteBuffer.allocateDirect(SQUARE_COORDS.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mSquareCoordsBuffer.put(SQUARE_COORDS).position(0);

        mTextureCoordsBuffer = ByteBuffer.allocateDirect(TEXTURE_COORDS.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureCoordsBuffer.put(TEXTURE_COORDS).position(0);

        shaderProgram = new SquareProgramId();
        shaderProgram.init(context);

        TextureHelper.loadTexture(context, "andy", "drawable");

        mPositionHandle = shaderProgram.get(IProgramId.POSITION);
        mMatrixHandle = shaderProgram.get(IProgramId.MATRIX);
        mTextureVertextHandle = shaderProgram.get(IProgramId.TEXTURE);

        return true;
    }

    @Override
    protected void drawFrame() {
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mSquareCoordsBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        float[] mvpMatrix = MatrixState.getMvpMatrix();

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glVertexAttribPointer(mTextureVertextHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mTextureCoordsBuffer);
        GLES20.glEnableVertexAttribArray(mTextureVertextHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureVertextHandle);
    }

    @Override
    public void sizeChanged(int width, int height) {

    }

}

