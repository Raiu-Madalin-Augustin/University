#include "Bag.h"
#include "BagIterator.h"
#include <exception>
#include <iostream>
using namespace std;

//Best case: Theta(1), Average case: Theta(1), Worst case: Theta(1)
Bag::Bag() {
	this->head = nullptr;
	this->tail = nullptr;
}

//Best case: Theta(1), Average case: Theta(1), Worst case: Theta(1)
void Bag::add(TElem elem) {
    PNode node_to_be_added = new Node(elem, nullptr, nullptr);

    if(this->head->void_node(this->head)){
        this->head = node_to_be_added;
        this->tail = node_to_be_added;
        return;
    }

    node_to_be_added->next = this->head;
    this->head->previous = node_to_be_added;
    this->head = node_to_be_added;
}

//Best case: Theta(1), Average case: O(n), Worst case: Theta(n)
bool Bag::remove(TElem elem) {
    if(!search(elem)) {
        return false;
    }
    else{
        PNode node_to_be_deleted = this->head;
        while(node_to_be_deleted != this-> tail && node_to_be_deleted->element != elem){
            node_to_be_deleted = node_to_be_deleted->next;
        }
        if(this->head == this->tail){
            this->head = nullptr;
            this->tail = nullptr;
            delete node_to_be_deleted;
            return true;
        }
        if(node_to_be_deleted == this->head){
            this->head = node_to_be_deleted->next;
            this->head->previous= nullptr;
            delete node_to_be_deleted;
            return true;
        }
        else if(node_to_be_deleted == this->tail){
            node_to_be_deleted->previous->next = nullptr;
            this->tail = this->tail->previous;
            delete node_to_be_deleted;
            return true;
        }
        else{
            node_to_be_deleted->previous->next = node_to_be_deleted->next;
            node_to_be_deleted->next->previous = node_to_be_deleted->previous;
            delete node_to_be_deleted;
            return true;
        }
    }
}

//Best case: Theta(n), Average case: O(n^2), Worst case: Theta(n^2)
//Best case - in case the element does not exist (and the DLL is empty, so search also has Theta(1) complexity)
//Average case - basically, we have O(k*n), where k is the number of occurrences of the given element
//               generally, k can be considered a constant, so the complexity would be O(n)
//               in case there is only one element in the bag, therefore we have to perform n steps in the for loop,
//               then k = n, the while loop from remove will be performed only one time (as there is only one elem, it
//               will always be found on the first position) and therefore we have Theta(n)
//Worst case - in the worst case, we will have number of occurrences k = n/2, and all of them would be in the second
//             half of the DLL, therefore there will be performed n/2 * n/2 steps, leading to a O(n^2) complexity
int Bag::removeAllOccurrences(TElem elem) {
    if(!search(elem)){
        return 0;
    }
    else{
        int numberOccurrences = this->nrOccurrences(elem);
        for(int index = 0; index < numberOccurrences; index++){
            remove(elem);
        }
        return numberOccurrences;
    }
}

//Best case: Theta(1), Average case: O(n), Worst case: Theta(n)
bool Bag::search(TElem elem) const {
    PNode first = this->head;

    if(this->head->void_node(this->head))
        return false;

    while(first != this->tail && first->element != elem){
        first = first->next;
    }

    if(first->element == elem)
        return true;
    else
        return false;
}

//Best case: Theta(1), Average case: O(n), Worst case: Theta(n)
int Bag::nrOccurrences(TElem elem) const {
    int counter = 0;
    PNode node = this->head;
    while(node != nullptr){
        if(node->element == elem){
            counter++;
        }
        node = node->next;
    }
    return counter;
}

//Best case: Theta(1), Average case: O(n), Worst case: Theta(n)
int Bag::size() const {
    int counter = 0;
    PNode node = this->head;
    while(node != nullptr){
        counter++;
        node = node->next;
    }
    return counter;
}

//Best case: Theta(1), Average case: Theta(1), Worst case: Theta(1)
bool Bag::isEmpty() const {
	if(this->size() == 0)
        return true;
	else
        return false;
}

//Best case: Theta(1), Average case: Theta(1), Worst case: Theta(1)
BagIterator Bag::iterator() const {
	return BagIterator(*this);
}

//Best case: Theta(1), Average case: O(n), Worst case: Theta(n)
Bag::~Bag() {
    while (this->head != nullptr) {
        PNode node = this->head;
        this->head = this->head->next;
        delete node;
    }
}



