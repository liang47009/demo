package com.yunfeng.nativecall;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

/**
 * renderer
 * Created by xll on 2018/8/31.
 */
public class MyRenderer implements GLSurfaceView.Renderer {
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    private float delta = 0.01f;
    private float defaultColor = 0.3f;

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GL_COLOR_BUFFER_BIT);
        defaultColor += delta;
        if (defaultColor > 1) {
            defaultColor = 0.01f;
        }
        GLES20.glClearColor(defaultColor, defaultColor, defaultColor, defaultColor);
    }
}
