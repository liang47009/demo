package com.yunfeng.gui.helper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import java.util.List;

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

    public static int loadTexture(Context context, String name, String defType) {
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
        return textureId[0];
    }

    /*
    * 加载一个cubeMap
    */
    public static int loadCubeMapTexture(Context context, List<Integer> picFilePathVec) {
        final int[] textureId = new int[1];
        GLES20.glGenTextures(1, textureId, 0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, textureId[0]);
        for (int i = 0; i < picFilePathVec.size(); i++) {
            final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), picFilePathVec.get(i));
            if (bitmap == null) {
                Log.e("app", "Error::loadCubeMapTexture could not load texture file:" + picFilePathVec.get(i));
                return 0;
            }
            GLUtils.texImage2D(GLES20.GL_TEXTURE_CUBE_MAP_POSITIVE_X + i, 0, bitmap, 0);
        }
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_CUBE_MAP, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, 0);
        return textureId[0];
    }

}
