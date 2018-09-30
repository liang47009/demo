package com.yunfeng.gui.ui;

import android.util.Log;

import com.yunfeng.Const;
import com.yunfeng.gui.render.IProgramId;
import com.yunfeng.gui.render.Position;

/**
 * view base
 * Created by xll on 2018/9/17.
 */
public abstract class View implements IGeometry {
    protected float[] mTransformMatrix = new float[16];
    protected float[] scratch = new float[16];

    protected IProgramId shaderProgram;

    protected Position mPosition = new Position(0.0f, 0.0f, 0.0f);

    @Override
    public void draw() {
        if (shaderProgram == null) {
            Log.e(Const.TAG, "shaderProgram is null");
            return;
        }
        shaderProgram.start();
        drawFrame();
//        shaderProgram.end();
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
