cmake_minimum_required(VERSION 3.4.1)

#-------------------------------------------------nativecall-------------------------------------------------------------
add_library( nativecall SHARED src/main/cpp/nativecall/updater.cpp)
target_link_libraries(nativecall log)
#---------------------------------------------------nativecall-----------------------------------------------------------

#----------------------------------------------------nativefork----------------------------------------------------------
add_library( nativefork SHARED src/main/cpp/nativefork/nativefork.cpp)
target_link_libraries(nativefork log)
#-----------------------------------------------------nativefork---------------------------------------------------------

#----------------------------------------------------sensor----------------------------------------------------------
include_directories( src/main/cpp/sensor )

add_library(sensor SHARED src/main/cpp/sensor/com_yunfeng_common_jni_JNILib.cpp
                          src/main/cpp/sensor/sensor/Sensor.cpp
                          src/main/cpp/sensor/sensor/TeapotRenderer.cpp
                          src/main/cpp/sensor/sensor/TeapotNativeActivity.cpp
                          src/main/cpp/sensor/renderer/Renderer.cpp
                          src/main/cpp/sensor/utils/CHelper.cpp
                          src/main/cpp/sensor/cpufeatures/cpu-features.c
                          src/main/cpp/sensor/native_app_glue/android_native_app_glue.c
                          src/main/cpp/sensor/ndk_helper/JNIHelper.cpp
                          src/main/cpp/sensor/ndk_helper/interpolator.cpp
                          src/main/cpp/sensor/ndk_helper/tapCamera.cpp
                          src/main/cpp/sensor/ndk_helper/gestureDetector.cpp
                          src/main/cpp/sensor/ndk_helper/perfMonitor.cpp
                          src/main/cpp/sensor/ndk_helper/vecmath.cpp
                          src/main/cpp/sensor/ndk_helper/GLContext.cpp
                          src/main/cpp/sensor/ndk_helper/shader.cpp
                          src/main/cpp/sensor/ndk_helper/gl3stub.c
                          )
target_link_libraries(sensor log android EGL GLESv2)
#----------------------------------------------------sensor-----------------------------------------------------------------
