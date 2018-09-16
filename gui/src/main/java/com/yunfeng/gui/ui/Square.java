package com.yunfeng.gui.ui;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.yunfeng.gui.helper.TextureHelper;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Square {
    Context mContext;

    // 正方形的顶点坐标
    private float[] SQUARE_COORDS = {-0.5f, -0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f};

    // 纹理的顶点坐标
    private float[] TEXTURE_COORDS = {0, 1, 1, 1, 0, 0, 1, 0};

    private static final int COORDS_PER_VERTEX = 2;

    private FloatBuffer mSquareCoordsBuffer;
    private FloatBuffer mTextureCoordsBuffer;

    private static final String VERTEX_SHADER = "attribute vec4 aPosition;" + "uniform mat4 uMVPMatrix;" + "attribute vec2 aTextureCoords;" + "varying vec2 vTextureCoords;" + "void main() {" + "    gl_Position = uMVPMatrix * aPosition;" + "    vTextureCoords = aTextureCoords;" + "}";

    private static final String FRAGMENT_SHADER = "precision mediump float;" + "uniform sampler2D uTextureUnit;" + // sampler2D 表示用于访问2D纹理的采样器
            "varying vec2 vTextureCoords;" + "void main() {" + "    gl_FragColor = texture2D(uTextureUnit, vTextureCoords);" + // 进行纹理采样
            "}";

    private static final String POSITION_NAME = "aPosition";
    private static final String COLOR_NAME = "vColor";
    private static final String MVP_MATRIX_NAME = "uMVPMatrix";
    private static final String TEXTURE_VERTEXT_NAME = "aTextureCoords";

    private int mPositionHandle;
    private int mColorHandle;
    private int mMatrixHandle;
    private int mTextureVertextHandle;

    private float[] mTransformMatrix = new float[16];

    public Square(Context context) {
        mContext = context;

        mSquareCoordsBuffer = ByteBuffer.allocateDirect(SQUARE_COORDS.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mSquareCoordsBuffer.put(SQUARE_COORDS).position(0);

        mTextureCoordsBuffer = ByteBuffer.allocateDirect(TEXTURE_COORDS.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mTextureCoordsBuffer.put(TEXTURE_COORDS).position(0);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);

        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        GLES20.glUseProgram(program);

        mPositionHandle = GLES20.glGetAttribLocation(program, POSITION_NAME);
        mColorHandle = GLES20.glGetUniformLocation(program, COLOR_NAME);
        mMatrixHandle = GLES20.glGetUniformLocation(program, MVP_MATRIX_NAME);
        mTextureVertextHandle = GLES20.glGetAttribLocation(program, TEXTURE_VERTEXT_NAME);

        TextureHelper.loadTexture(context, "andy", "drawable");

        Matrix.setIdentityM(mTransformMatrix, 0);
        Matrix.rotateM(mTransformMatrix, 0, 90, 0, 0, 1.0f);
    }

    private static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    public void draw(float[] mvpMatrix) {
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mSquareCoordsBuffer);
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        Matrix.multiplyMM(mvpMatrix, 0, mvpMatrix, 0, mTransformMatrix, 0);

        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mvpMatrix, 0);

        GLES20.glVertexAttribPointer(mTextureVertextHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, mTextureCoordsBuffer);
        GLES20.glEnableVertexAttribArray(mTextureVertextHandle);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mTextureVertextHandle);
    }

}

