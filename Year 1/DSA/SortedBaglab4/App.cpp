#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <iostream>
#include "ShortTest.h"
#include "ExtendedTest.h"
#include <cassert>

bool relation5(TComp r1, TComp r2) {
    return r1 <= r2;
}

//ADT SortedBag–using a hashtable with separate chaining in which the elements are stored.
// If an elements appears multiple times, it will be stored multiple times in the hashtable.

using namespace std;

int main() {
    testAll();
    cout << "Short tests over" << endl;

    testAllExtended();

    cout << "Tests over" << endl;

    SortedBag sortedBag(relation5);
    sortedBag.add(9);
    sortedBag.add(11);
    sortedBag.add(8);
    sortedBag.add(9);
    sortedBag.add(44);
    sortedBag.add(2005);
    SortedBagIterator iterator = sortedBag.iterator();
    while (iterator.valid()) {
        std::cout << iterator.getCurrent() << std::endl;
        iterator.next();
    }
    cout << "[INFO] Added all elements" << endl;
    iterator.first();
    assert(iterator.remove() == 8);
    assert(iterator.remove() == 9);
    iterator.next();
    assert(iterator.remove() == 11);
    cout<<"[INFO] Only 3 elements left"<<endl;
    iterator.first();
    while (iterator.valid()) {
        std::cout << iterator.getCurrent() << std::endl;
        iterator.next();
    }

    iterator.first();
    assert(iterator.remove() == 9);
    assert(iterator.remove() == 44);

    assert(iterator.remove() == 2005);
    assert(iterator.valid() == false);
    cout << "Removed all" << endl;

    iterator.first();
    while (iterator.valid()) {
        std::cout << iterator.getCurrent() << std::endl;
        iterator.next();
    }
    //system("pause");
}
