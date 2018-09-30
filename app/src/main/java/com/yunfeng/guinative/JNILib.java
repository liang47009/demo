package com.yunfeng.guinative;

/**
 * jni
 * Created by xll on 2018/9/13.
 */
public class JNILib {

    static {
        System.loadLibrary("guinative");
    }

    public native static void nativeOnSurfaceCreated(Object assets);

    public native static void nativeOnSurfaceChanged(int width, int height);

    public native static void nativeOnDrawFrame();
}
