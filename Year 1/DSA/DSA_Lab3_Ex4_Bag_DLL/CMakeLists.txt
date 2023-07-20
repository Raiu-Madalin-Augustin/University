cmake_minimum_required(VERSION 3.17)
project(DSA_Lab3_Ex4_Bag_DLL)

set(CMAKE_CXX_STANDARD 14)

include_directories(DSA_Lab3_Ex4_Bag_DLL)

add_executable(DSA_Lab3_Ex4_Bag_DLL
        DSA_Lab3_Ex4_Bag_DLL/App.cpp
        DSA_Lab3_Ex4_Bag_DLL/Bag.cpp
        DSA_Lab3_Ex4_Bag_DLL/Bag.h
        DSA_Lab3_Ex4_Bag_DLL/BagIterator.cpp
        DSA_Lab3_Ex4_Bag_DLL/BagIterator.h
        DSA_Lab3_Ex4_Bag_DLL/ExtendedTest.cpp
        DSA_Lab3_Ex4_Bag_DLL/ExtendedTest.h
        DSA_Lab3_Ex4_Bag_DLL/Node.cpp
        DSA_Lab3_Ex4_Bag_DLL/Node.h
        DSA_Lab3_Ex4_Bag_DLL/ShortTest.cpp
        DSA_Lab3_Ex4_Bag_DLL/ShortTest.h)
