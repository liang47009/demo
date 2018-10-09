cmake_minimum_required(VERSION 3.4.1)

#----------------------------------------------------vulkanapp-----------------------------------------------------------------
include_directories( ../common )

add_library(vulkanapp SHARED VulkanAndroid.cpp VulkanRenderer.cpp NativeAndroid.cpp)
target_compile_definitions(vulkanapp PRIVATE VK_NO_PROTOTYPES VK_USE_PLATFORM_ANDROID_KHR )  #USE_FREEIMAGE
target_link_libraries(vulkanapp log android EGL GLESv2
                -Wl,-whole-archive common -Wl,-no-whole-archive
                breakpad ) #freeimage_static
#----------------------------------------------------vulkanapp-----------------------------------------------------------------
