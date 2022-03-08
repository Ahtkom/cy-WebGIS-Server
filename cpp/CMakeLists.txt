cmake_minimum_required(VERSION 3.0.0)
project(webgis_native_c VERSION 0.1.0)

include_directories(${PROJECT_SOURCE_DIR}/include)

include_directories(/usr/lib/jvm/java-11-openjdk-amd64/include)

include_directories(/home/Ahtkom/pmk/dependencies/geos/include)

include_directories(/home/Ahtkom/pmk/project/ccyy/include)

aux_source_directory(${PROJECT_SOURCE_DIR}/src SRCS)

set(LIBRARY_OUTPUT_PATH /usr/lib)
add_library(webgis_native_c SHARED ${SRCS})

target_link_libraries(webgis_native_c
/home/Ahtkom/pmk/dependencies/geos/build/lib/libgeos.so
/home/Ahtkom/pmk/project/ccyy/build/Release/linux/libccyy.so
)