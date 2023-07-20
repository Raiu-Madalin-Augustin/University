#include "SortedBag.h"
#include "SortedBagIterator.h"
#include <cmath>

SortedBag::SortedBag(Relation r) : relation(r) {
    for (int i = 0; i < hashGroups; i++)
        hashTable[i] = nullptr;
    //Theta(hashGroups)
}

void SortedBag::add(TComp e) {
    int position = hashFunction(e);
    this->elements++;

    Node *new_node = new Node;
    new_node->value = e;

    if (hashTable[position] == nullptr || !relation(hashTable[position]->value, e)) {
        new_node->next = hashTable[position];
        hashTable[position] = new_node;
    } else {
        Node *current = hashTable[position];
        Node *previous;
        while (current != nullptr && relation(current->value, e)) {
            previous = current;
            current = current->next;
        }
        new_node->next = previous->next;
        previous->next = new_node;
    }
    if (get_loaded_factor() > this->load_factor) {
        resize();
    }
    //Theta(Hashgroups)

}


bool SortedBag::remove(TComp elem) {

    if (this->elements == 0)
        return false;

    int position = hashFunction(elem);
    Node *current = hashTable[position];
    Node *previous = nullptr;
    while (current != nullptr) {
        if (current->value == elem) {
            if (previous == nullptr) {
                hashTable[hashFunction(elem)] = current->next;
                delete current;
            } else {
                previous->next = current->next;
                delete current;
            }
            elements--;
            return true;
        }
        previous = current;
        current = current->next;
    }
    return false;

    //Theta(number of elems coresponding to that key)

}


bool SortedBag::search(TComp elem) const {
    int position = hashFunction(elem);
    Node *node = hashTable[position];
    while (node != nullptr && relation(node->value, elem)) {
        if (node->value == elem) return true;
        node = node->next;
    }
    return false;
    //Theta(number of elems coresponding to that key)
}


int SortedBag::nrOccurrences(TComp elem) const {
    int nrOcc = 0;
    int pos = hashFunction(elem);
    Node *current = hashTable[pos];
    while (current != nullptr) {
        if (current->value == elem) {
            nrOcc++;
        }
        current = current->next;
    }
    return nrOcc;
    //Theta(number of elems coresponding to that key)
}


int SortedBag::size() const {

    return this->elements;
    //Theta(1)
}


bool SortedBag::isEmpty() const {
    return (this->elements == 0);
    //Theta(1)
}


SortedBagIterator SortedBag::iterator()  {
    return SortedBagIterator(*this);
}


SortedBag::~SortedBag() {
    for (int i = 0; i < hashGroups; i++) {
        Node *node = hashTable[i];
        while (node != nullptr) {
            Node *next = node->next;
            delete node;
            node = next;
        }
    }
    //Theta(nrelems)
}

int SortedBag::hashFunction(int element) const {
    return abs(element) % hashGroups;
    //Theta(1)
}

double SortedBag::get_loaded_factor() const {
    return (double(elements) / hashGroups);
    //Theta(1)
}

void SortedBag::resize() {
    int *all_values = new int[this->hashGroups];
    int counter = 0;

    SortedBagIterator it(*this);
    it.first();
    while (it.valid()) {
        all_values[++counter] = it.getCurrent();
        it.next();
    }

    Node **old_table = hashTable;

    int old_hashGroups = this->hashGroups;
    this->hashGroups *= 2;
    this->hashGroups += 1;

    hashTable = new Node *[hashGroups];
    for (int i = 0; i < hashGroups; i++)
        hashTable[i] = nullptr;

    this->elements = 0;

    for (int i = 1; i <= counter; i++)
        add(all_values[i]);

    for (int i = 0; i < old_hashGroups; i++) {
        Node *node = old_table[i];
        while (node != nullptr) {
            Node *next = node->next;
            delete node;
            node = next;
        }
    }

    delete[] old_table;
    //Theta(hashGroups)
}
