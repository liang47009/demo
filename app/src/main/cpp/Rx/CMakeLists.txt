cmake_minimum_required(VERSION 3.2 FATAL_ERROR)

project(rxcpp VERSION 2.2.4 LANGUAGES C CXX)

set_property(GLOBAL PROPERTY USE_FOLDERS ON)

# define some folders

get_filename_component(RXCPP_DIR "${CMAKE_CURRENT_SOURCE_DIR}/cpp" PATH)
get_filename_component(RXCPP_DIR "${RXCPP_DIR}" PATH)

include_directories( v2/src )

# The list of RxCpp source files. Please add every new file to this list
set(RX_SOURCES
   ${RXCPP_DIR}/Rx/native_lib.cpp
)

# Grouping all the source files puts them into a virtual folder in Visual Studio
source_group("src" FILES ${RX_SOURCES})

add_library(RxCpp SHARED ${RX_SOURCES})
SET_TARGET_PROPERTIES(RxCpp PROPERTIES LINKER_LANGUAGE CXX)
