cmake_minimum_required(VERSION 3.17)
project(DSA_Lab4_Ex4_Matrix_OpenAdressing)

set(CMAKE_CXX_STANDARD 14)

add_executable(DSA_Lab4_Ex4_Matrix_OpenAdressing
        App.cpp
        ExtendedTest.h
        ExtendedTest.cpp
        Matrix.h
        Matrix.cpp
        ShortTest.h
        ShortTest.cpp MatrixIterator.cpp MatrixIterator.h)