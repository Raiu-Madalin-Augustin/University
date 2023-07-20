#pragma once

#include "SortedBag.h"

//Change the iterator to be able to remove the current element. Add the following operation in the SortedBagIterator class:
////removes and returns the current element from the iterator
////after the operation the current element from the Iterator is the next element from the SortedBag,
/// or, if the removed element was the last one, the iterator is invalid
////throws exception if the iterator is invalid
//TElem remove();
//Obs: In order for this operation to work, you need to perform some other changes in your code:
//Iterator operation from the SortedBag no longer is const
//The reference to the SortedBag in the iterator is no longer const (but it is still a reference!)
//The parameter passed to the constructor of the iterator class is no longer const


class SortedBag;

class SortedBagIterator {
    friend class SortedBag;

private:
    SortedBag &bag;

    SortedBagIterator( SortedBag &b);

    TElem current_element = NULL_TCOMP;

    Node **current;

public:
    TComp getCurrent();

    bool valid();

    void next();

    void first();
    //removes and returns the current element from the iterator
   //after the operation the current element from the Iterator is the next element from the SortedBag,
   //or, if the removed element was the last one, the iterator is invalid
   //throws exception if the iterator is invalid
    TElem remove();
};

