#pragma once
//DO NOT INCLUDE SORTEDBAGITERATOR

//DO NOT CHANGE THIS PART


//ADT SortedBag–using a hashtable with separate chaining in which the elements are stored.
// If an elements appears multiple times, it will be stored multiple times in the hashtable.

typedef int TComp;
typedef TComp TElem;
typedef bool(*Relation)(TComp, TComp);
#define NULL_TCOMP -11111;

class SortedBagIterator;

struct Node {
    TElem value;
    Node *next;
};

class SortedBag {
	friend class SortedBagIterator;

private:
    int hashGroups=10;
    Node** hashTable = new Node* [hashGroups];
    int elements = 0;
    double load_factor = 0.7;
    Relation relation;


    int hashFunction(int element) const;
    double get_loaded_factor() const;
    void resize();

public:
	//constructor
	explicit SortedBag(Relation r);

	//adds an elements to the sorted bag
	void add(TComp e);

	//removes one occurence of an elements from a sorted bag
	//returns true if an elements was removed, false otherwise (if e was not part of the sorted bag)
	bool remove(TComp e);

	//checks if an elements appearch is the sorted bag
	bool search(TComp e) const;

	//returns the number of occurrences for an elements in the sorted bag
	int nrOccurrences(TComp e) const;

	//returns the number of elements from the sorted bag
	int size() const;

	//returns an iterator for this sorted bag
	SortedBagIterator iterator() ;

	//checks if the sorted bag is empty
	bool isEmpty() const;

	//destructor
	~SortedBag();
};