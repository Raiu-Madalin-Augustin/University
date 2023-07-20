#include <exception>
#include "BagIterator.h"
#include "Bag.h"
#include <iostream>

using namespace std;


BagIterator::BagIterator(const Bag& c): bag(c)
{
    this->current = c.head;
}

void BagIterator::first() {
    this->current = this->bag.head;
}

void BagIterator::last() {
    this->current = this->bag.tail;
}

void BagIterator::next() {
    if(!this->valid())
    {
        throw exception();
    }
    this->current = this->current->get_next();
}

void BagIterator::previous() {
    if(!this->valid())
    {
        throw exception();
    }
    this->current = this->current->get_previous();
}

bool BagIterator::valid() const {
    return this->current != nullptr;
}



TElem BagIterator::getCurrent() const
{
    if(!this->valid())
    {
        throw exception();
    }
	return this->current->get_element();
}
