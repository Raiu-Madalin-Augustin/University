#include "Bag.h"

class BagIterator
{
	//DO NOT CHANGE THIS PART
	friend class Bag;
	
private:
	const Bag& bag;
	PNode current;
	BagIterator(const Bag& c);
public:
	void first();
    void last();
	void next();
    void previous();
	TElem getCurrent() const;
	bool valid() const;
};
