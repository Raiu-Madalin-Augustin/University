#include "Matrix.h"
#include <exception>
#include <iostream>

using namespace std;


Matrix::Matrix(int nrLines, int nrCols) {
    lines = nrLines;
    columns = nrCols;
    m = compute_m(lines, columns);
    size = 0;
    table = new TPair[m];
    for (int i = 0; i < m; i++) {
        table[i] = NULL_TPAIR;
    }
}


int Matrix::nrLines() const {
	return this->lines;
}


int Matrix::nrColumns() const {
    return this->columns;
}


TElem Matrix::element(int i, int j) const {
    if (i >= nrLines() || j >= nrColumns() || i < 0 || j < 0)
        throw exception();
    int k = 0;
    int pos;
    bool found = false;
    int v = NULL_TELEM;

    do {
        pos = hash(i, j, k);
        if (get<0>(table[pos]) == i && get<1>(table[pos]) == j) {
            v = get<2>(table[pos]);
            found = true;
        }
        else ++k;
    } while (k < m && !found);

    return v;
}

TElem Matrix::modify(int i, int j, TElem e) {
    if (i >= nrLines() || j >= nrColumns() || i < 0 || j < 0)
        throw exception();

    int k = 0;
    bool found = false;
    TElem oldValue;
    do {
        int pos = hash(i, j, k);
        if (table[pos] == NULL_TPAIR || get<0>(table[pos]) == i && get<1>(table[pos]) == j) {
            oldValue = get<2>(table[pos]);
            table[pos] = TPair(i, j, e);
            found = true;
        }
        else ++k;
    } while (k < m && !found);

    if (k == m && !found) {
        resize_and_rehash();
        modify(i, j, e);
    }
    else size++;

    return oldValue;
}

int Matrix::hash(int i, int j, int k) const {
    // Computing the hash value
    return ((int)((i + j) % m + (double)(C1 * k + C2 * k * k)) % m);
}

void Matrix::resize_and_rehash() {
    int old_m = m;
    m = 2 * m;
    TPair* newTable = new TPair[m];

    for(int i = 0; i < m; i++){
        newTable[i] = NULL_TPAIR;
    }

    for (int i = 0; i < old_m; i++) {
        int k = 0;
        bool found = false;

        do {
            int hv = hash(get<0>(table[i]), get<1>(table[i]), k);
            if (newTable[hv] == NULL_TPAIR) {
                newTable[hv] = table[i];
                found = true;
            }
            else ++k;
        } while (k < m && !found);
    }

    delete[] table;
    table = newTable;
}

int Matrix::compute_m(int lin, int col) {
    int min_val = lin + col;
    while (!power_of_2(min_val)) {
        min_val++;
    }
    return min_val;
}

bool Matrix::power_of_2(int n) {
    if(n == 0) {
        return false;
    }
    while(n != 1)
    {
        n = n/2;
        if(n %2 != 0 && n != 1) {
            return false;
        }
    }
    return true;
}

MatrixIterator Matrix::iterator() const {
    return MatrixIterator(*this);
}
