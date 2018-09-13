cmake_minimum_required(VERSION 3.4.1)

add_subdirectory(freeimage)
#----------------------------------------------------gui----------------------------------------------------------
include_directories( src/main/cpp src/main/cpp/include ../app/src/main/jni/include )

add_library(ndk_helper STATIC IMPORTED)
set_target_properties(ndk_helper PROPERTIES IMPORTED_LOCATION ${Project_SOURCE_DIR}/../app/jniLibs/${ANDROID_ABI}/libndk_helper.a)

add_library(gui SHARED  src/main/cpp/com_yunfeng_gui_JNILib.cpp
                        src/main/cpp/renderer/Renderer.cpp)

target_link_libraries(gui log android EGL GLESv2 ndk_helper freeiamge_shared)
#----------------------------------------------------gui-----------------------------------------------------------------
