package com.yunfeng.demo.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * bitmap
 * Created by xll on 2018/7/27.
 */
public class BitmapUtils {
    public static Bitmap W(Bitmap paramBitmap) {
        int i = paramBitmap.getHeight();
        Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), i, Bitmap.Config.ARGB_8888);
        Canvas localCanvas = new Canvas(localBitmap);
        Paint localPaint = new Paint();
        ColorMatrix localColorMatrix = new ColorMatrix();
        localColorMatrix.set(new float[]{0.9F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.9F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.9F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F});
        localPaint.setColorFilter(new ColorMatrixColorFilter(localColorMatrix));
        localCanvas.drawBitmap(paramBitmap, 0.0F, 0.0F, localPaint);
        return localBitmap;
    }
}
