package com.yunfeng.gui.ui;

import android.util.Log;

import com.yunfeng.gui.render.IProgramId;

/**
 * view base
 * Created by xll on 2018/9/17.
 */
public abstract class View implements IGeometry {
    protected float[] mTransformMatrix = new float[16];
    protected float[] scratch = new float[16];

    protected IProgramId shaderProgram;

    @Override
    public void draw() {
        if (shaderProgram == null) {
            Log.e("app", "shaderProgram is null");
            return;
        }
        shaderProgram.start();
        drawFrame();
        shaderProgram.end();
    }

//    public IProgramId getShaderProgram() {
//        return shaderProgram;
//    }
//
//    public void setShaderProgram(IProgramId shaderProgram) {
//        this.shaderProgram = shaderProgram;
//    }

    protected abstract void drawFrame();
}
