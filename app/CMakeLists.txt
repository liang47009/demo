cmake_minimum_required(VERSION 3.4.1)

#-------------------------------------------------updater-------------------------------------------------------------
#add_library( updater SHARED src/main/jni/updater.cpp
#                            src/main/jni/Clazz.cpp)
#target_link_libraries(updater log)
#---------------------------------------------------updater-----------------------------------------------------------

#----------------------------------------------------app----------------------------------------------------------
#add_library( app SHARED src/main/jni/app.cpp)
#target_link_libraries(app log updater)
#-----------------------------------------------------app---------------------------------------------------------

#----------------------------------------------------sensor----------------------------------------------------------
#include_directories( src/main/jni src/main/jni/include )

#add_library(cpufeatures STATIC IMPORTED)
#set_target_properties(cpufeatures PROPERTIES IMPORTED_LOCATION ${Project_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libcpufeatures.a)

#add_library(ndk_helper STATIC IMPORTED)
#set_target_properties(ndk_helper PROPERTIES IMPORTED_LOCATION ${Project_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libndk_helper.a)

#add_library(sensor SHARED src/main/jni/sensor/Sensor.cpp
#                          src/main/jni/sensor/TeapotRenderer.cpp
#                          src/main/jni/utils/CHelper.cpp
#                          src/main/jni/com_yunfeng_common_jni_JNILib.cpp)

#target_link_libraries(sensor log ndk_helper cpufeatures android EGL GLESv2)
#----------------------------------------------------sensor-----------------------------------------------------------------
