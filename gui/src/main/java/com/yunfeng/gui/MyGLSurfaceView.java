package com.yunfeng.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.AttributeSet;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * sdf
 * Created by xll on 2018/9/14.
 */
public class MyGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {
    private Context mContext;
    private Square mSquare;

    private float[] mMVPMatrix = new float[16];
    private float[] mProjectionMatrix = new float[16];
    private float[] mCameraMatrix = new float[16];

    public MyGLSurfaceView(Context context) {
        super(context);
        mContext = context;
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0.5f, 0);
        mSquare = new Square(mContext);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / (float) height;
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1f, 1f, 3f, 7f);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Matrix.setLookAtM(mCameraMatrix, 0, 0, 0, 5f, 0, 0, 0, 0, 1f, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mCameraMatrix, 0);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mSquare.draw(mMVPMatrix);
    }
}

class Square {
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

        TextureHelper.loadTexture(context.getResources(), R.drawable.andy);

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

class TextureHelper {

    public static int loadTexture(Resources res, int resId) {
        final int[] textureId = new int[1];
        // 生成文理ID
        GLES20.glGenTextures(1, textureId, 0);
        // 绑定文理ID
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        // 设置缩小时采用最近点采样
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        // 设置放大时采用线性采样
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        final Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        // 加载纹理进显存
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        return textureId[0];
    }

}
