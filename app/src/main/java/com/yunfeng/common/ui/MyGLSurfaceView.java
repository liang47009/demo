package com.yunfeng.common.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.yunfeng.common.jni.JNILib;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;

/**
 * my
 * Created by xll on 2018/8/31.
 */
public class MyGLSurfaceView extends GLSurfaceView {
    public MyGLSurfaceView(Context context) {
        super(context);

        this.setEGLContextClientVersion(2);
        this.setRenderer(new MyRenderer());
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    /**
     * renderer
     * Created by xll on 2018/8/31.
     */
    final class MyRenderer implements GLSurfaceView.Renderer {

        private float delta = 0.01f;
        private float defaultColor = 0.3f;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            AssetManager assetManager = MyGLSurfaceView.this.getContext().getResources().getAssets();
            JNILib.nativeOnSurfaceCreated(assetManager);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            JNILib.nativeOnSurfaceChanged(width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            JNILib.nativeOnDrawFrame();
//            GLES20.glClear(GL_COLOR_BUFFER_BIT);
//            defaultColor += delta;
//            if (defaultColor > 1) {
//                defaultColor = 0.01f;
//            }
//            GLES20.glClearColor(defaultColor, defaultColor, defaultColor, defaultColor);
        }

    }

}
