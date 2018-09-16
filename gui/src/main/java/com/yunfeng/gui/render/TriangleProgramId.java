package com.yunfeng.gui.render;

import android.content.Context;
import android.opengl.GLES20;

import com.yunfeng.gui.helper.FileHelper;
import com.yunfeng.gui.helper.ShaderHelper;

import java.util.HashMap;
import java.util.Map;

public class TriangleProgramId implements IProgramId {
    private static final String POSITION_NAME = "aPosition";
    private static final String COLOR_NAME = "vColor";
    private static final String MVP_MATRIX_NAME = "uMVPMatrix";
    private static final String TEXTURE_VERTEXT_NAME = "aTextureCoords";

    private Map<String, Integer> handlers = new HashMap<>(16);

    public void init(Context context) {
        String mVertexShader = FileHelper.readShaderFromStream(context, "shaders/triangle.vert");
        String mfragmentShader = FileHelper.readShaderFromStream(context, "shaders/triangle.frag");
        int vertexShader = ShaderHelper.loadShader(GLES20.GL_VERTEX_SHADER, mVertexShader);
        int fragmentShader = ShaderHelper.loadShader(GLES20.GL_FRAGMENT_SHADER, mfragmentShader);
        int program = GLES20.glCreateProgram();
        GLES20.glAttachShader(program, vertexShader);
        GLES20.glAttachShader(program, fragmentShader);
        GLES20.glLinkProgram(program);
        GLES20.glUseProgram(program);

        int mPositionHandle = GLES20.glGetAttribLocation(program, POSITION_NAME);
        int mColorHandle = GLES20.glGetUniformLocation(program, COLOR_NAME);
        int mMatrixHandle = GLES20.glGetUniformLocation(program, MVP_MATRIX_NAME);
        int mTextureVertextHandle = GLES20.glGetAttribLocation(program, TEXTURE_VERTEXT_NAME);

        handlers.put(POSITION, mPositionHandle);
        handlers.put(COLOR, mColorHandle);
        handlers.put(MATRIX, mMatrixHandle);
        handlers.put(TEXTURE, mTextureVertextHandle);

    }

    @Override
    public int get(String key) {
        return handlers.get(key);
    }

}
