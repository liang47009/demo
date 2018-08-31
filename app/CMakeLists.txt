cmake_minimum_required(VERSION 3.4.1)

add_library( app SHARED src/main/jni/app.cpp)

add_library( updater SHARED src/main/jni/updater.cpp
                            src/main/jni/Clazz.cpp)

target_link_libraries(app log updater)
target_link_libraries(updater log)