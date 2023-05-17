//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_CONSTANT_H
#define HELLOOBOE_CONSTANT_H

#include "Function.h"

template <typename T>
struct Constant : public Function<T> {

    Constant(
            double start,
            double length,
            int sampleRate
            ) : Function<T>(start, start, length, sampleRate) {
            Function<T>::minY = start;
            Function<T>::maxY = start;
    }

    double fun(double x){
            return c;
    }

private:
    double c;
};

#endif //HELLOOBOE_CONSTANT_H
