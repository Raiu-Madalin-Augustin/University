//
// Created by user on 05/04/2021.
//

#ifndef DSA_LAB3_EX4_BAG_DLL_NODE_H
#define DSA_LAB3_EX4_BAG_DLL_NODE_H

typedef int TElem;

class Node;

typedef Node *PNode;

class Node {
private:

    TElem element;
    PNode next;
    PNode previous;

public:

    friend class Bag;

    Node(TElem element, PNode next, PNode previous);

    TElem get_element();

    PNode get_next();

    PNode get_previous();

    bool void_node(PNode possibly_void_node) const;
};


#endif //DSA_LAB3_EX4_BAG_DLL_NODE_H
