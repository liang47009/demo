cmake_minimum_required(VERSION 3.4.1)

#----------------------------------------------------common----------------------------------------------------------
add_library(common STATIC cpufeatures/cpu-features.c
                          native_app_glue/android_native_app_glue.c
                          ndk_helper/JNIHelper.cpp
                          ndk_helper/interpolator.cpp
                          ndk_helper/tapCamera.cpp
                          ndk_helper/gestureDetector.cpp
                          ndk_helper/perfMonitor.cpp
                          ndk_helper/vecmath.cpp
                          ndk_helper/GLContext.cpp
                          ndk_helper/shader.cpp
                          ndk_helper/gl3stub.c)
#----------------------------------------------------common-----------------------------------------------------------------
