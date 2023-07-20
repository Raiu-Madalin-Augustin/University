#include "ListIterator.h"
#include "SortedIndexedList.h"
#include <iostream>

using namespace std;

ListIterator::ListIterator(const SortedIndexedList& list) : list(list) {
	this->currentPosition = 0;
}

void ListIterator::first(){
	this->currentPosition = 0;
}

void ListIterator::next(){

	if (!this->valid())
	{
		exception myex;
		throw myex;
	}
	++this->currentPosition;
}

bool ListIterator::valid() const{
	if (this->currentPosition < list.size())return true;
	return false;
}

TComp ListIterator::getCurrent() const{
	if (!this->valid())
	{
		exception myex;
		throw myex;
	}
	return list.getElement(this->currentPosition);
}


void SortedIndexedList::add(TComp e) {
    DNode newNode = new Node(e, nullptr, nullptr);

    if (listSize == 0) {
        head = newNode;
        tail = newNode;
        listSize++;
        return;
    }

    bool found = false;
    DNode current = head;
    while (current->get_next() != nullptr) {

        if (r(e, current->get_element())) {
            if (current->get_previous() == nullptr) {
                newNode->set_next(current);
                current->set_previous(newNode);
                head = newNode;

            }
            else {
                current->get_previous()->set_next(newNode);
                newNode->set_previous(current->get_previous());
                newNode->set_next(current);
                current->set_previous(newNode);

            }
            found = true;
            break;
        }
        current = current->get_next();
    }

    if (found == false) {
        newNode->set_previous(current);
        current->set_next(newNode);
        tail = newNode;
    }
    listSize++;
}