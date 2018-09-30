cmake_minimum_required(VERSION 3.4.1)

#----------------------------------------------------guinative-----------------------------------------------------------------
add_library(breakpad_client STATIC IMPORTED)
set_target_properties(breakpad_client PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libbreakpad_client.a)

add_library(guinative SHARED    com_yunfeng_gui_JNILib.cpp
								VulkanAndroid.cpp
								renderer/vecmath.cpp
								renderer/Renderer.cpp
								renderer/utils.cpp
								renderer/Square.cpp
								renderer/VulkanRenderer.cpp
								renderer/MatrixStat.cpp
								renderer/Triangle.cpp)
target_compile_definitions(guinative PRIVATE VK_NO_PROTOTYPES VK_USE_PLATFORM_ANDROID_KHR)
target_link_libraries(guinative log android EGL GLESv2 common breakpad_client)
#----------------------------------------------------guinative-----------------------------------------------------------------
