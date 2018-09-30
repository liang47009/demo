package com.yunfeng.guinative;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * sdf
 * Created by xll on 2018/9/14.
 */
public class MyGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        JNILib.nativeOnSurfaceCreated(this.getContext().getAssets());
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        JNILib.nativeOnSurfaceChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        JNILib.nativeOnDrawFrame();
    }
}
