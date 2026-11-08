cmake_minimum_required(VERSION 3.22.1)

project("litegba")

# Define your native shared library target
add_library(
    litegba
    SHARED
    native-lib.cpp
)

# Find Android system libraries (such as the Android logging library)
find_library(
    log-lib
    log
)

# Link your shared library with the Android system libraries
target_link_libraries(
    litegba
    ${log-lib}
)
