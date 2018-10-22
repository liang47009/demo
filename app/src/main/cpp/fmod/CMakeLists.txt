cmake_minimum_required(VERSION 3.4.1)

#-------------------------------------------------fmodnative-------------------------------------------------------------

add_library(fmod SHARED IMPORTED)
set_target_properties(fmod PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libfmodL.so)

add_library(fmodstudio SHARED IMPORTED)
set_target_properties(fmodstudio PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/jniLibs/${ANDROID_ABI}/libfmodstudioL.so)

include_directories( inc )
add_library( fmodnative SHARED fmod.cpp common.cpp common_platform.cpp FmodSound.cpp)
target_link_libraries(fmodnative log fmod fmodstudio )
#---------------------------------------------------fmodnative-----------------------------------------------------------
