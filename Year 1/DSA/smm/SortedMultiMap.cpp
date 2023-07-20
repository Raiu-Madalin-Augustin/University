#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>
#include <vector>
#include <exception>
using namespace std;

SortedMultiMap::SortedMultiMap(Relation r)
{
	_relation = r;
	_root = -1;
	_size = 0;
	_cap = 50;
	_elems = new TElem[_cap];
	_left = new int[_cap];
	_right = new int[_cap];
	for (int i = 0; i < _cap; i++)
	{
		_left[i] = i + 1;
		_right[i] = -1;
	}
	_left[_cap - 1] = -1;
	_right[_cap - 1] = -1;
	_firstEmpty = 0;
}

void SortedMultiMap::add(TKey c, TValue v)
{
	TElem elem = make_pair(c, v);
	_root = Insert(_root, elem);
	_size++;
}

int SortedMultiMap::Insert(int node, TElem elem)
{
	if (node == -1)
		node == createNode(elem);
	else
	{
		if (_relation(elem.first, node[_elems].first))
			_left[node] = Insert(_left[node], elem);
		else
			_right[node] = Insert(_left[node], elem);
	}
	return node;
}

vector<TValue> SortedMultiMap::search(TKey c) const
{
	std::vector<TValue> values;
	int current = _root;

	while (current != -1)
	{
		if (_relation(c, _elems[current].first))
		{
			if (_elems[current].first == c)
				values.push_back(_elems[current].second);
			current = _left[current];
		}
		else
		{
			if (_elems[current].first == c)
				values.push_back(_elems[current].second);
			current = _right[current];
		}
	}
	return values;
}

bool SortedMultiMap::remove(TKey c, TValue v) 
{
	TElem elem = make_pair(c, v);
	bool found = false;
	_root = Remove2(_root, elem, found);
	if (found)
	{
		_size--;
		return true;
	}
	return false;
}

bool SortedMultiMap::Remove2(int node, TElem elem, bool found)
{
	if (node == -1)
		return false;
	else
	{
		int tmp;
		int aux;
		
		if (_relation(_elems[node].first, elem.first) && _relation(elem.first, _elems[node].first)
			&& _elems[node].second == elem.second)//the node we want to remove
		{
			found = true;
			if (_left[node] != -1 && _right[node] != -1)
			{
				tmp = getMin(_right[node]);//replace the keys
				_elems[node].first = _elems[tmp].first;
				_elems[node].second = _elems[tmp].second;

				_right[node] = Remove2(_right[node], _elems[node], found);// delete the min key from right tree
				return node;
			}
			else
			{
				tmp = node;
				if (_left[node] != -1)
					aux = _right[node];
				else
					aux = _left[node];
			}
			return aux;
		}
		else if (_relation(elem.first, _elems[node].first))
		{
			_left[node] = Remove2(_left[node], elem, found);
			return node;
		}
		else 
		{
			_right[node] = Remove2(_right[node], elem, found);
			return node;
		}

	}
}

int SortedMultiMap::size() const
{
	return _size;
}

int SortedMultiMap::getMin(int node) const
{
	while (_left[node] != -1)
	{
		node = _left[node];
	}
	return node;
}

int SortedMultiMap::getMax(int node) const
{
	while (_right[node] != -1)
	{
		node = _right[node];
	}
	return node;
}

bool SortedMultiMap::isEmpty() const
{
	if (_size == 0)
		return true;
	return false;
}

//best:theta(1)
//worst:theta(cap)
//total:O(cap)
int SortedMultiMap::getKeyRange() const
{
	int dif;
	int current = _root;
	while (current != -1)
	{
		dif = getMax(_elems[current].first) - getMin(_elems[current].first);
	}
	return dif;
}

SMMIterator SortedMultiMap::iterator() const {
	return SMMIterator(*this);
}

int SortedMultiMap::allocate()
{
	int aux = _firstEmpty;
	_firstEmpty = _left[_firstEmpty];
	return aux;
}

int SortedMultiMap::createNode(TElem elem)
{
	int i = allocate();
	if (i != -1)
	{
		_elems[i] = elem;
		_left[i] = -1;
		_right[i] = -1;
	}
	return i;
}

SortedMultiMap::~SortedMultiMap()
{
	delete[] _elems;
	delete[] _left;
	delete[] _right;
}
