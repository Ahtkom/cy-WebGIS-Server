cmake_minimum_required(VERSION 3.0.0)
project(cywebgis VERSION 0.1.0)

include_directories(${CMAKE_CURRENT_SOURCE_DIR}/include)

aux_source_directory(${CMAKE_CURRENT_SOURCE_DIR}/src SRCS)

add_library(cywebgis SHARED ${SRCS})