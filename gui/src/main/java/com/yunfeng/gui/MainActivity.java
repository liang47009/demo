package com.yunfeng.gui;

import android.app.Activity;
import android.content.res.AssetManager;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * main
 * Created by xll on 2018/9/11.
 */
public class MainActivity extends Activity {

    static {
        System.loadLibrary("gui");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setLayoutParams(params);

//        MyGLSurfaceView view = new MyGLSurfaceView(this);
        View view = createSystemView();

        layout.addView(view);
        setContentView(layout);
    }

    public View createSystemView() {
        GLSurfaceView view = new GLSurfaceView(this);
        view.setEGLContextClientVersion(2);
        view.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        view.setRenderer(new GLSurfaceView.Renderer() {
            @Override
            public void onSurfaceCreated(GL10 gl, EGLConfig config) {
                JNILib.nativeOnSurfaceCreated(MainActivity.this.getAssets());
            }

            @Override
            public void onSurfaceChanged(GL10 gl, int width, int height) {
                JNILib.nativeOnSurfaceChanged(width, height);
            }

            @Override
            public void onDrawFrame(GL10 gl) {
                JNILib.nativeOnDrawFrame();
            }
        });
        view.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
        return view;
    }


    public void test() {
//        Matrix.frustumM();
//        Matrix.setLookAtM();
//        Matrix.multiplyMM();
//        GLES20.glUniformMatrix4fv();
//        GLUtils.texImage2D();
    }
}
