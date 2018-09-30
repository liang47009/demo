cmake_minimum_required(VERSION 3.4.1)

#----------------------------------------------------nativefork----------------------------------------------------------
add_library( nativefork SHARED nativefork.cpp)
target_link_libraries(nativefork log)
#-----------------------------------------------------nativefork---------------------------------------------------------
