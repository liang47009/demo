package com.yunfeng.common.jni;

/**
 * jni
 * Created by xll on 2018/9/7.
 */
public class JNILib {

    public native static void nativeOnSurfaceCreated(Object assetManager);

    public native static void nativeOnDrawFrame();

    public native static void nativeOnSurfaceChanged(int width, int height);

    public native static void nativeUnloadLibrary(long handler);

    public native static long nativeLoadLibrary(String path);
}
