cmake_minimum_required(VERSION 3.17)
project(DSA_Lab3_Ex4_Bag_DLL)

set(CMAKE_CXX_STANDARD 14)

add_executable(DSA_Lab3_Ex4_Bag_DLL
        App.cpp
        Bag.cpp
        Bag.h
        BagIterator.cpp
        BagIterator.h
        ExtendedTest.cpp
        ExtendedTest.h
        ShortTest.cpp
        ShortTest.h Node.cpp Node.h)