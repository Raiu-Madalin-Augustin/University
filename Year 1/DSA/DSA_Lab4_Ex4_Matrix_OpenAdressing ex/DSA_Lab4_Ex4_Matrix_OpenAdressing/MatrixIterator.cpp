//
// Created by user on 12/05/2021.
//
#include "Matrix.h"
#include "MatrixIterator.h"
using namespace std;

MatrixIterator::MatrixIterator(const Matrix& mat) : matrix(mat) {
    this->i = 0;
    this->j = 0;
    this->current = getCurrent();
}

void MatrixIterator::first() {
    this->i = 0;
    this->j = 0;
    this->current = getCurrent();
}

void MatrixIterator::next() {
    if (!valid()) {
        throw exception();
    }
    if (j == matrix.columns - 1) {
        i++;
        j = 0;
    }
    else
        j++;
}

TElem MatrixIterator::getCurrent() {
    if (!valid()) {
        throw exception();
    }
    int k = 0;
    bool found = false;
    do {
        int pos = matrix.hash(i, j, k);
        if (get<0>(matrix.table[pos]) == i && get<1>(matrix.table[pos]) == j) {
            current = get<2>(matrix.table[pos]);
            found = true;
        }
        else ++k;
    } while (k < matrix.m && !found);
    return current;
}

bool MatrixIterator::valid() const {
    if (i >= matrix.lines || j >= matrix.columns || i < 0 || j < 0)
        return false;
    return true;
}
