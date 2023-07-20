
#include <iostream>
#include "Matrix.h"
#include "ExtendedTest.h"
#include "ShortTest.h"

using namespace std;


int main() {

	testAll();
	testAllExtended();
	cout << "Test End" << endl;
	system("pause");
}

/*
ADT Matrix–represented as a sparse matrix where <line, column, value> triples (value ≠0) are memorized, ordered
lexicographically considering the line and column of every element.
The elements are stored in a hashtable with open addressing, quadratic probing.
*/