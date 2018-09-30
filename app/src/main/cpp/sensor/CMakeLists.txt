cmake_minimum_required(VERSION 3.4.1)

#----------------------------------------------------sensor----------------------------------------------------------
add_library(sensor SHARED com_yunfeng_common_jni_JNILib.cpp
                          sensor/Sensor.cpp
                          sensor/TeapotRenderer.cpp
                          sensor/TeapotNativeActivity.cpp
                          renderer/Renderer.cpp
                          utils/CHelper.cpp)
target_link_libraries(sensor log android EGL GLESv2 common)
#----------------------------------------------------sensor-----------------------------------------------------------------
