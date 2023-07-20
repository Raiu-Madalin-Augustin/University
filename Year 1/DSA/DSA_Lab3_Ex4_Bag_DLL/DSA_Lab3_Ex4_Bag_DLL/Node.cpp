//
// Created by user on 05/04/2021.
//

#include "Node.h"

Node::Node(TElem element, PNode next, PNode previous) {
    this->element = element;
    this->next = next;
    this->previous = previous;
}

//Complexity: Theta(1)
TElem Node::get_element() {
    return this->element;
}

//Complexity: Theta(1)
PNode Node::get_next() {
    return this->next;
}

//Complexity: Theta(1)
PNode Node::get_previous() {
    return this->previous;
}

//Complexitate: Theta(1)
bool Node::void_node(PNode possibly_void_node) const {
    PNode node = possibly_void_node;
    if(node == nullptr)
        return true;
    return false;
}