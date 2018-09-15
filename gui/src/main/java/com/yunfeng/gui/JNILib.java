package com.yunfeng.gui;

/**
 * jni
 * Created by xll on 2018/9/13.
 */
public class JNILib {

    public native static void nativeOnSurfaceCreated(Object assets);

    public native static void nativeOnSurfaceChanged(int width, int height);

    public native static void nativeOnDrawFrame();
}
