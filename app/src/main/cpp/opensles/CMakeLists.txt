cmake_minimum_required(VERSION 3.4.1)

#----------------------------------------------------nativeaudio----------------------------------------------------------
add_library( nativeaudio SHARED native-audio-jni.c)
target_link_libraries(nativeaudio log android OpenSLES)
#-----------------------------------------------------nativeaudio---------------------------------------------------------
