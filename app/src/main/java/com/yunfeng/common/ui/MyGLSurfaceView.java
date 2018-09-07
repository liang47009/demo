package com.yunfeng.common.ui;

import android.content.Context;
import android.content.res.AssetManager;
import android.opengl.GLSurfaceView;

import com.yunfeng.common.jni.JNILib;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * my
 * Created by xll on 2018/8/31.
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private List<RendererDrawCallback> _rendererDrawCallbacks = new ArrayList<RendererDrawCallback>();

    public MyGLSurfaceView(Context context) {
        super(context);

        this.setEGLContextClientVersion(2);
        this.setRenderer(new MyRenderer());
        this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    public interface RendererDrawCallback {
        void onDrawFrame();
    }

    public void addDrawCallback(RendererDrawCallback callbackObj) {
        if (callbackObj != null && !_rendererDrawCallbacks.contains(callbackObj)) {
            _rendererDrawCallbacks.add(callbackObj);
        }
    }

    public void removeDrawCallback(RendererDrawCallback callbackObj) {
        if (callbackObj != null && _rendererDrawCallbacks.contains(callbackObj)) {
            _rendererDrawCallbacks.remove(callbackObj);
        }
    }

    /**
     * renderer
     * Created by xll on 2018/8/31.
     */
    final class MyRenderer implements GLSurfaceView.Renderer {

        private final static long NANOSECONDSPERSECOND = 1000000000L;
        private final static long NANOSECONDSPERMICROSECOND = 1000000;
        private long _animationInterval = (long) (1.0 / 60 * NANOSECONDSPERSECOND);
        private long _lastTickInNanoSeconds;

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            AssetManager assetManager = MyGLSurfaceView.this.getContext().getResources().getAssets();
            JNILib.nativeOnSurfaceCreated(assetManager);
            this._lastTickInNanoSeconds = System.nanoTime();
        }

        public void setAnimationInterval(final float animationInterval) {
            _animationInterval = (long) (animationInterval * NANOSECONDSPERSECOND);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            JNILib.nativeOnSurfaceChanged(width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            /*
             * No need to use algorithm in default(60 FPS) situation,
             * since onDrawFrame() was called by system 60 times per second by default.
             */
            if (_animationInterval <= 1.0 / 60 * NANOSECONDSPERSECOND) {
                JNILib.nativeOnDrawFrame();
            } else {
                final long now = System.nanoTime();
                final long interval = now - this._lastTickInNanoSeconds;

                if (interval < _animationInterval) {
                    try {
                        long sleepValue = (_animationInterval - interval) / NANOSECONDSPERMICROSECOND;
                        if (sleepValue <= 0) sleepValue = 1;
                        Thread.sleep(sleepValue);
                    } catch (final Exception e) {
                        e.printStackTrace();
                    }
                }
                /*
                * Render time MUST be counted in, or the FPS will slower than appointed.
                */
                this._lastTickInNanoSeconds = System.nanoTime();
                JNILib.nativeOnDrawFrame();
                for (int i = 0; i < _rendererDrawCallbacks.size(); i++) {
                    _rendererDrawCallbacks.get(i).onDrawFrame();
                }
            }
        }
    }

}
