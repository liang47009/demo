cmake_minimum_required(VERSION 3.4.1)

#----------------------------------------------------guinative-----------------------------------------------------------------
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
target_link_libraries(guinative log android EGL GLESv2 common)
#----------------------------------------------------guinative-----------------------------------------------------------------
