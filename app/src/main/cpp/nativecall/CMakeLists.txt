cmake_minimum_required(VERSION 3.4.1)

#-------------------------------------------------nativecall-------------------------------------------------------------
add_library( nativecall SHARED updater.cpp)
target_link_libraries(nativecall log)
#---------------------------------------------------nativecall-----------------------------------------------------------
