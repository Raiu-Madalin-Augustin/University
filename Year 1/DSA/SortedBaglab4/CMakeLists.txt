cmake_minimum_required(VERSION 3.16)
project(SortedBaglab4)

set(CMAKE_CXX_STANDARD 14)

include_directories(.)

add_executable(SortedBaglab4
        App.cpp
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h
        SortedBag.cpp
        SortedBag.h
        SortedBagIterator.cpp
        SortedBagIterator.h)
