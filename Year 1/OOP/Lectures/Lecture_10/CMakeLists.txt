cmake_minimum_required(VERSION 3.17)
project(Lecture_10)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_AUTOMOC ON)
set(CMAKE_AUTOUIC ON)
set(CMAKE_AUTORCC ON)
include_directories(.)
include_directories(genes)
include_directories(genes_custom_model)

add_executable(Lecture_10
        genes/DialogGenesListWidget.cpp
        genes/DialogGenesListWidget.h
        genes/DialogGenesView.cpp
        genes/DialogGenesView.h
        genes/GeneRepository.cpp
        genes/GeneRepository.h
        genes/lecture11_demo.cpp
        genes/lecture11_demo.h
        genes/main.cpp
        genes_custom_model/GeneRepository.cpp
        genes_custom_model/GeneRepository.h
        genes_custom_model/GenesTableModel.cpp
        genes_custom_model/GenesTableModel.h
        genes_custom_model/lecture11_demo_custom_models.cpp
        genes_custom_model/lecture11_demo_custom_models.h
        genes_custom_model/main.cpp
        genes_custom_model/PaginatedGenesTableModel.cpp
        genes_custom_model/PaginatedGenesTableModel.h
        genes_custom_model/PictureDelegate.cpp
        genes_custom_model/PictureDelegate.h)
set(CMAKE_PREFIX_PATH "C:\\Qt\\5.15.2\\mingw81_64")
find_package(Qt5Widgets REQUIRED)
target_link_libraries(Lecture_10 Qt5::Widgets)