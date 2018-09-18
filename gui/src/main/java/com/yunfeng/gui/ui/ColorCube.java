package com.yunfeng.gui.ui;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.content.Context;
import android.opengl.GLES20;

import com.yunfeng.gui.helper.TextureHelper;
import com.yunfeng.gui.render.IProgramId;
import com.yunfeng.gui.render.MatrixState;
import com.yunfeng.gui.render.ProgramIdManager;

/**
 * color cube
 * Created by xll on 2018/9/18.
 */
public class ColorCube extends View {

    //顶点坐标
    private FloatBuffer vertexBuffer;
    //颜色坐标
    private FloatBuffer colorBuffer;
    //float类型的字节数
    private static final int BYTES_PER_FLOAT = 4;
    //共有72个顶点坐标，每个面包含12个顶点坐标
    private static final int POSITION_COMPONENT_COUNT = 12 * 6;
    // 数组中每个顶点的坐标数
    private static final int COORDS_PER_VERTEX = 3;

    private int mMatrixHandle = -1;
    private int mPositionHandle = -1;
    private int mTextureVertextHandle = -1;
    private int mColorVertextHandle = -1;

    private static float vertices[] = {
            //前面
            0, 0, 1.0f, 1.0f, //
            1.0f, 1.0f, -1.0f, 1.0f, //
            1.0f, 0, 0, 1.0f,//
            -1.0f, 1.0f, 1.0f, -1.0f, //
            -1.0f, 1.0f, 0, 0, //
            1.0f, -1.0f, -1.0f, 1.0f,//
            1.0f, -1.0f, 1.0f, 0,//
            0, 1.0f, 1.0f, -1.0f, //
            1.0f, 1.0f, 1.0f, 1.0f,//
            //后面
            0, 0, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 0, 0, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 0, 0, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 0, 0, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f,
            //左面
            -1.0f, 0, 0, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 0, 0, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 0, 0, -1.0f, -1.0f, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 0, 0, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f, 1.0f,
            //右面
            1.0f, 0, 0, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 0, 0, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 0, 0, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0, 0, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f,
            //上面
            0, 1.0f, 0, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 0, 1.0f, 0, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 0, 1.0f, 0, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, 0, 1.0f, 0, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
            //下面
            0, -1.0f, 0, 1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 0, -1.0f, 0, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, -1.0f, 0, -1.0f, 0, -1.0f, -1.0f, -1.0f, 1.0f, -1.0f, -1.0f, 0, -1.0f, 0, 1.0f, -1.0f, -1.0f, 1.0f, -1.0f, 1.0f};

    //顶点颜色值数组，每个顶点4个色彩值RGBA
    private static float colors[] = new float[]{
            //前面
            1, 1, 1, 0,//中间为白色
            1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0,//中间为白色
            1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0,//中间为白色
            1, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0,//中间为白色
            1, 0, 0, 0, 1, 0, 0, 0,
            //后面
            1, 1, 1, 0,//中间为白色
            0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0,//中间为白色
            0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0,//中间为白色
            0, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0,//中间为白色
            0, 0, 1, 0, 0, 0, 1, 0,
            //左面
            1, 1, 1, 0,//中间为白色
            1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0,//中间为白色
            1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0,//中间为白色
            1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0,//中间为白色
            1, 0, 1, 0, 1, 0, 1, 0,
            //右面
            1, 1, 1, 0,//中间为白色
            1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0,//中间为白色
            1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0,//中间为白色
            1, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0,//中间为白色
            1, 1, 0, 0, 1, 1, 0, 0,
            //上面
            1, 1, 1, 0,//中间为白色
            0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0,//中间为白色
            0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0,//中间为白色
            0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0,//中间为白色
            0, 1, 0, 0, 0, 1, 0, 0,
            //下面
            1, 1, 1, 0,//中间为白色
            0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0,//中间为白色
            0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0,//中间为白色
            0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0,//中间为白色
            0, 1, 1, 0, 0, 1, 1, 0,};

    public boolean init(Context context) {
        vertexBuffer = ByteBuffer.allocateDirect(vertices.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        // 把坐标们加入FloatBuffer中
        vertexBuffer.put(vertices);
        // 设置buffer，从第一个坐标开始读
        vertexBuffer.position(0);

        //颜色buffer
        colorBuffer = ByteBuffer.allocateDirect(colors.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        shaderProgram = ProgramIdManager.getInstance().getProgrameId(ProgramIdManager.TRIANGLE_PROGRAM);
        shaderProgram.init(context);

//        textureId = TextureHelper.loadTexture(context, "andy", "drawable");

        mPositionHandle = shaderProgram.get(IProgramId.POSITION);
        mMatrixHandle = shaderProgram.get(IProgramId.MATRIX);
        mTextureVertextHandle = shaderProgram.get(IProgramId.TEXTURE);
        mColorVertextHandle = shaderProgram.get(IProgramId.COLOR);

        return true;
    }

//    private int textureId = -1;

    @Override
    public void drawFrame() {
        float[] mvpMatrix = MatrixState.getMvpMatrix();
        GLES20.glUniformMatrix4fv(mMatrixHandle, 1, false, mvpMatrix, 0);

        //---------传入顶点数据数据
        GLES20.glEnableVertexAttribArray(mPositionHandle);
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);

        //---------传入颜色数据
        GLES20.glEnableVertexAttribArray(mColorVertextHandle);
        GLES20.glVertexAttribPointer(mColorVertextHandle, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, colorBuffer);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, POSITION_COMPONENT_COUNT);

        GLES20.glDisableVertexAttribArray(mPositionHandle);
        GLES20.glDisableVertexAttribArray(mColorVertextHandle);
    }

    @Override
    public void sizeChanged(int width, int height) {

    }

    @Override
    public void setPosition(float x, float y, float z) {

    }

}
