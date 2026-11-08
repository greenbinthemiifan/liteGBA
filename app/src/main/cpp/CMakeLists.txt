cmake_minimum_required(VERSION 3.22.1)

project("litegba")

# Add mGBA source directories (adjust paths based on your folder structure)
add_subdirectory(mgba)

# Define your native shared library output (liblitegba.so)
add_library(
    litegba
    SHARED
    native-lib.cpp
)

# Find system libraries required for Android native development (like logging)
find_library(
    log-lib
    log
)

# Link mGBA and Android system libraries to your shared library
target_link_libraries(
    litegba
    mgba          # The target name from mGBA's CMake configuration
    ${log-lib}
)
