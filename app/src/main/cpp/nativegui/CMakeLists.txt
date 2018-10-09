cmake_minimum_required(VERSION 3.4.1)

#----------------------------------------------------guinative-----------------------------------------------------------------
include_directories( ../common ../common/googlebreakpad/src )

add_library(curl STATIC IMPORTED)
set_target_properties(curl PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libcurl.a)

add_library(crypto STATIC IMPORTED)
set_target_properties(crypto PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libcrypto.a)

add_library(ssl STATIC IMPORTED)
set_target_properties(ssl PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libssl.a)

add_library(guinative SHARED    com_yunfeng_gui_JNILib.cpp
								VulkanAndroid.cpp
								renderer/vecmath.cpp
								renderer/Renderer.cpp
								renderer/utils.cpp
								renderer/Square.cpp
								renderer/VulkanRenderer.cpp
								renderer/MatrixStat.cpp
								renderer/Triangle.cpp)
target_compile_definitions(guinative PRIVATE VK_NO_PROTOTYPES VK_USE_PLATFORM_ANDROID_KHR USE_FREEIMAGE)
target_link_libraries(guinative log android EGL GLESv2 common breakpad curl ssl crypto zlib freeimage_static)
#----------------------------------------------------guinative-----------------------------------------------------------------
