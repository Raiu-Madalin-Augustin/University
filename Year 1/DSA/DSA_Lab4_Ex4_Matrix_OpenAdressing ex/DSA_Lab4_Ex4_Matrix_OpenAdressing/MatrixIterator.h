//
// Created by user on 12/05/2021.
//

#ifndef DSA_LAB4_EX4_MATRIX_OPENADRESSING_MATRIXITERATOR_H
#define DSA_LAB4_EX4_MATRIX_OPENADRESSING_MATRIXITERATOR_H

#include <utility>
#include <exception>
#include "Matrix.h"


class MatrixIterator {
    //DO NOT CHANGE THIS PART
    friend class Matrix;
private:
    const Matrix& matrix;
    TElem current;
    int i;
    int j;
    MatrixIterator(const Matrix& m);
public:

    void first();
    void next();
    TElem getCurrent();
    bool valid() const;
};

#endif //DSA_LAB4_EX4_MATRIX_OPENADRESSING_MATRIXITERATOR_H
