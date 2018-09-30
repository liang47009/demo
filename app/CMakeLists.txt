cmake_minimum_required(VERSION 3.4.1)

INCLUDE_DIRECTORIES(src/main/cpp/common)

ADD_SUBDIRECTORY(src/main/cpp/common)
ADD_SUBDIRECTORY(src/main/cpp/nativecall)
ADD_SUBDIRECTORY(src/main/cpp/nativefork)
ADD_SUBDIRECTORY(src/main/cpp/sensor)
