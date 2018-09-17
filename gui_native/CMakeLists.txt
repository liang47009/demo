cmake_minimum_required(VERSION 3.4.1)

add_subdirectory(freeimage)
#----------------------------------------------------gui----------------------------------------------------------
include_directories( src/main/cpp src/main/cpp/include src/main/cpp/cpufeatures src/main/cpp/native_app_glue )

add_library(cpufeatures STATIC src/main/cpp/cpufeatures/cpu-features.c)

add_library(android_native_app_glue STATIC src/main/cpp/native_app_glue/android_native_app_glue.c)

add_library(ndk_helper STATIC src/main/cpp/ndkhelper/gestureDetector.cpp
                              src/main/cpp/ndkhelper/gl3stub.c
                              src/main/cpp/ndkhelper/GLContext.cpp
                              src/main/cpp/ndkhelper/interpolator.cpp
                              src/main/cpp/ndkhelper/JNIHelper.cpp
                              src/main/cpp/ndkhelper/perfMonitor.cpp
                              src/main/cpp/ndkhelper/shader.cpp
                              src/main/cpp/ndkhelper/tapCamera.cpp
                              src/main/cpp/ndkhelper/vecmath.cpp)

add_library(gui SHARED  src/main/cpp/com_yunfeng_gui_JNILib.cpp
                        src/main/cpp/renderer/vecmath.cpp
                        src/main/cpp/renderer/Renderer.cpp
                        src/main/cpp/renderer/utils.cpp
                        src/main/cpp/renderer/Square.cpp
                        src/main/cpp/renderer/Triangle.cpp)

target_link_libraries(gui log android EGL GLESv2 ndk_helper freeimage_static cpufeatures android_native_app_glue )
#----------------------------------------------------gui-----------------------------------------------------------------
