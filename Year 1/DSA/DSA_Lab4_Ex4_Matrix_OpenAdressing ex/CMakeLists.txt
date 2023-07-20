cmake_minimum_required(VERSION 3.17)
project(DSA_Lab4_Ex4_Matrix_OpenAdressing_ex)

set(CMAKE_CXX_STANDARD 14)

include_directories(DSA_Lab4_Ex4_Matrix_OpenAdressing)

add_executable(DSA_Lab4_Ex4_Matrix_OpenAdressing_ex
        DSA_Lab4_Ex4_Matrix_OpenAdressing/App.cpp
        DSA_Lab4_Ex4_Matrix_OpenAdressing/ExtendedTest.cpp
        DSA_Lab4_Ex4_Matrix_OpenAdressing/ExtendedTest.h
        DSA_Lab4_Ex4_Matrix_OpenAdressing/Matrix.cpp
        DSA_Lab4_Ex4_Matrix_OpenAdressing/Matrix.h
        DSA_Lab4_Ex4_Matrix_OpenAdressing/MatrixIterator.cpp
        DSA_Lab4_Ex4_Matrix_OpenAdressing/MatrixIterator.h
        DSA_Lab4_Ex4_Matrix_OpenAdressing/ShortTest.cpp
        DSA_Lab4_Ex4_Matrix_OpenAdressing/ShortTest.h)
