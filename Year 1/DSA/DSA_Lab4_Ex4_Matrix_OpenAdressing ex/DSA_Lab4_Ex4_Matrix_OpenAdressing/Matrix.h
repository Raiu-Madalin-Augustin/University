#pragma once

#include <tuple>


//DO NOT CHANGE THIS PART
typedef int TElem;
typedef std::tuple<int, int, TElem> TPair;
#define NULL_TELEM 0
#define NULL_TPAIR std::make_tuple(-1, -1, NULL_TELEM)
#define C1 0.5
#define C2 0.5
class MatrixIterator;

class Matrix {
    friend class MatrixIterator;

private:
	int lines;
	int columns;
    TPair* table;
    int m;
    int size;
    static int compute_m(int lin, int col) ;
    static bool power_of_2(int n);
    int hash(int i, int j, int k) const;
    void resize_and_rehash();

public:
	//constructor
	Matrix(int nrLines, int nrCols);

	//returns the number of lines
	int nrLines() const;

	//returns the number of columns
	int nrColumns() const;

	//returns the element from line i and column j (indexing starts from 0)
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem element(int i, int j) const;

	//modifies the value from line i and column j
	//returns the previous value from the position
	//throws exception if (i,j) is not a valid position in the Matrix
	TElem modify(int i, int j, TElem e);

	//creates an iterator over the elements of the Matrix that are not equal to NULL_TELEM.
	//The iterator will return the elements by column (first elements of the first column, then from second column, etc)
    MatrixIterator iterator() const;
};
