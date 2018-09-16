package com.yunfeng.gui.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;

public class TextureHelper {

    public static int getResId(Context context, String name, String defType) {
        return context.getResources().getIdentifier(name, defType, context.getPackageName());
    }

    public static int loadTexture(Resources res, int resId) {
        final int[] textureId = new int[1];
        // 生成文理ID
        GLES20.glGenTextures(1, textureId, 0);
        // 绑定文理ID
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        // 设置缩小时采用最近点采样
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        // 设置放大时采用线性采样
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        final Bitmap bitmap = BitmapFactory.decodeResource(res, resId);
        // 加载纹理进显存
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

        return textureId[0];
    }

    public static void loadTexture(Context context, String name, String defType) {
        final int[] textureId = new int[1];
        // 生成文理ID
        GLES20.glGenTextures(1, textureId, 0);
        // 绑定文理ID
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId[0]);
        // 设置缩小时采用最近点采样
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        // 设置放大时采用线性采样
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);

        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), getResId(context, name, defType));
        // 加载纹理进显存
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);

        bitmap.recycle();

    }
}
