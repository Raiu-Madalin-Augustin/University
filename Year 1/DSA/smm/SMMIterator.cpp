#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <iostream>

SMMIterator::SMMIterator(const SortedMultiMap& d) : map(d)
{
	_current = map._root;
}

void SMMIterator::first()
{
	_current = map._root;
	while (_current != -1)
	{
		stack.push(_current);
		_current = map._left[_current];
	}
	if (!stack.empty())
		_current = stack.top();
}

void SMMIterator::next()
{
	if (valid()) 
	{
	//	_current = stack.top();
	//	stack.pop();
	//	if (map._right[_current] != -1) {
	//		_current = map._right[_current];
	//		while (_current != -1)
	//		{
	//			stack.push(_current);
	//			_current = map._left[_current];
	//		}
	//	}
	//	if (!stack.empty())
	//		_current = stack.top();
	//	else
	//		_current = -1;
	//}
	//else {
	//	std::cout<<"";
	}
}

bool SMMIterator::valid() const\
{
	if (_current != -1)
		return true;
	return false;
}

TElem SMMIterator::getCurrent() const
{
	if (valid())
		return pair<TKey, TValue>(map._elems[_current].first, map._elems[_current].second);
	else
		throw exception();
}


