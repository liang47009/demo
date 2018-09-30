package com.yunfeng.gui.test;

import android.content.Context;
import android.opengl.GLES20;
import android.util.Log;

import com.yunfeng.gui.render.IRenderer;
import com.yunfeng.gui.render.MatrixState;
import com.yunfeng.gui.ui.IGeometry;

import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glViewport;

/**
 * test
 * Created by xll on 2018/9/19.
 */
public class TestRenderer implements IRenderer {

    private Context context;

    public TestRenderer(Context context) {
        this.context = context;
    }

    //  Circle circle;
    private TestCube cube;

    public boolean init() {
        Log.w("MyRender", "onSurfaceCreated");
        //设置屏幕背景色RGBA
        glClearColor(0.5f, 0.5f, 0.5f, 1.0f);
//      //打开深度检测
        GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//        //打开背面剪裁
        GLES20.glEnable(GLES20.GL_CULL_FACE);
//      circle = new Circle(context);
        cube = new TestCube(context);
        return true;
    }

    public void sizeChanged(int width, int height) {
        glViewport(0, 0, width, height);
        float ratio = (float) width / height;
        //设置投影矩阵
//      circle.projectionMatrix(width, height);
        // 调用此方法计算产生透视投影矩阵
        MatrixState.frustumM(-ratio, ratio, -1, 1, 20, 100);
        // 调用此方法产生摄像机9参数位置矩阵
        MatrixState.setLookAtM(-16f, 8f, 45, 0f, 0f, 0f, 0f, 1.0f, 0.0f);
        MatrixState.multiplyMM(0, 0, 0);
    }

    public void drawFrame() {
//      glClear(GL_COLOR_BUFFER_BIT);
        //清除深度缓冲与颜色缓冲
        glClear(GLES20.GL_DEPTH_BUFFER_BIT | GLES20.GL_COLOR_BUFFER_BIT);
//      circle.draw();
        cube.draw();
    }

    @Override
    public void addView(IGeometry geometry, float x, float y) {

    }

    @Override
    public void onTouch(float x, float y) {

    }

}
