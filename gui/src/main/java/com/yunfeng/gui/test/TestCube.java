package com.yunfeng.gui.test;

/**
 * Created by xll on 2018/9/19.
 */

import android.content.Context;
import android.opengl.GLES20;

import com.yunfeng.gui.helper.FileHelper;
import com.yunfeng.gui.helper.ShaderHelper;
import com.yunfeng.gui.render.MatrixState;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class TestCube {

    //顶点坐标
    private FloatBuffer vertexBuffer;
    //颜色坐标
    private FloatBuffer colorBuffer;
    private Context context;
    //float类型的字节数
    private static final int BYTES_PER_FLOAT = 4;
    //共有72个顶点坐标，每个面包含12个顶点坐标
    private static final int POSITION_COMPONENT_COUNT = 12 * 6;
    // 数组中每个顶点的坐标数
    private static final int COORDS_PER_VERTEX = 3;
    // 颜色数组中每个颜色的值数
    private static final int COORDS_PER_COLOR = 4;

    private static final String A_POSITION = "aPosition";
    private static final String A_COLOR = "aColor";
    private static final String U_MATRIX = "uMVPMatrix";
    private int uMatrixLocation;
    private int aColorLocation;
    private int aPositionLocation;
    private int program;

    static float vertices[] = {
            //前面
            0, 0, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 0, 0, 1.0f, -1.0f, 1.0f, 1.0f, -1.0f, -1.0f, 1.0f, 0, 0, 1.0f, -1.0f, -1.0f, 1.0f, 1.0f, -1.0f, 1.0f, 0, 0, 1.0f, 1.0f, -1.0f, 1.0f, 1.0f, 1.0f, 1.0f,
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
    static float colors[] = new float[]{
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

    public TestCube(Context context) {
        this.context = context;

        vertexBuffer = ByteBuffer.allocateDirect(vertices.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        // 把坐标们加入FloatBuffer中
        vertexBuffer.put(vertices);
        // 设置buffer，从第一个坐标开始读
        vertexBuffer.position(0);

        //颜色buffer
        colorBuffer = ByteBuffer.allocateDirect(colors.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);

        //获取顶点着色器文本
        String vertexShaderSource = FileHelper.readShaderFromStream(context, "shaders/triangle.vert");
        //获取片段着色器文本
        String fragmentShaderSource = FileHelper.readShaderFromStream(context, "shaders/triangle.frag");
        //获取program的id
        int vertexShader = ShaderHelper.loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderSource);
        int fragmentShader = ShaderHelper.loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderSource);
        program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        GLES20.glUseProgram(program);

        aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);

        //---------传入顶点数据数据
        GLES20.glVertexAttribPointer(aPositionLocation, COORDS_PER_VERTEX, GLES20.GL_FLOAT, false, 0, vertexBuffer);
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        //---------传入颜色数据
        GLES20.glVertexAttribPointer(aColorLocation, COORDS_PER_COLOR, GLES20.GL_FLOAT, false, 0, colorBuffer);
        GLES20.glEnableVertexAttribArray(aColorLocation);
    }

    public void draw() {
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, MatrixState.getMvpMatrix(), 0);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, POSITION_COMPONENT_COUNT);
    }
}
