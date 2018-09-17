package com.yunfeng.gui.view;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.yunfeng.gui.render.GLES2Renderer;
import com.yunfeng.gui.render.IRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * sdf
 * Created by xll on 2018/9/14.
 */
public class MyGLSurfaceView extends GLSurfaceView implements GLSurfaceView.Renderer {

    private IRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);
        setRenderer(this);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        mRenderer = new GLES2Renderer(context);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mRenderer.init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mRenderer.sizeChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mRenderer.drawFrame();
    }
}