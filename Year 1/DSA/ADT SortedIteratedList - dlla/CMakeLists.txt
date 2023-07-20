cmake_minimum_required(VERSION 3.17)
project(ADT_SortedIteratedList___dlla)

set(CMAKE_CXX_STANDARD 14)

include_directories("ADT SortedIteratedList - dlla")

add_executable(ADT_SortedIteratedList___dlla
        "ADT SortedIteratedList - dlla/App.cpp"
        "ADT SortedIteratedList - dlla/ExtendedTest.cpp"
        "ADT SortedIteratedList - dlla/ExtendedTest.h"
        "ADT SortedIteratedList - dlla/ListIterator.cpp"
        "ADT SortedIteratedList - dlla/ListIterator.h"
        "ADT SortedIteratedList - dlla/ShortTest.cpp"
        "ADT SortedIteratedList - dlla/ShortTest.h"
        "ADT SortedIteratedList - dlla/SortedIteratedList.cpp"
        "ADT SortedIteratedList - dlla/SortedIteratedList.h")
