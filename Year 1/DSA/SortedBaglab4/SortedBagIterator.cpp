#include "SortedBagIterator.h"
#include "SortedBag.h"
#include <exception>

using namespace std;

SortedBagIterator::SortedBagIterator(SortedBag &b) : bag(b) {

    current = new Node *[b.hashGroups];
    for (int i = 0; i < bag.hashGroups; i++)
        current[i] = bag.hashTable[i];
    bag.relation(1,2);
    first();
    //O(hashGroups)
}

TComp SortedBagIterator::getCurrent() {
    if (!valid())
        throw std::exception();
    return current_element;
}

bool SortedBagIterator::valid() {
    return current_element != NULL_TCOMP;
}

void SortedBagIterator::next() {
    if (!valid())
        throw std::exception();

    Node *next = nullptr;
    int position;
    for (int i = 0; i < bag.hashGroups; i++) {
        if (current[i] == nullptr) continue;

        if (next == nullptr || bag.relation(current[i]->value, next->value)) {
            next = current[i];
            position = i;
        }
    }
    if (next != nullptr) {
        current_element = next->value;
        current[position] = current[position]->next;
    } else
        current_element = NULL_TCOMP;
}

void SortedBagIterator::first() {
    for (int i = 0; i < bag.hashGroups; i++)
        current[i] = bag.hashTable[i];

    Node *first = nullptr;
    int position;
    for (int i = 0; i < bag.hashGroups; i++) {
        if (current[i] == nullptr) continue;

        if (first == nullptr || bag.relation(current[i]->value, first->value)) {
            first = current[i];
            position = i;
        }
    }

    if (first != nullptr) {
        current_element = first->value;
        current[position] = current[position]->next;
    } else
        current_element = NULL_TCOMP;
}

TElem SortedBagIterator::remove() {
    int to_remove = current_element;
    bag.remove(to_remove);
    next();
    return to_remove;
}


