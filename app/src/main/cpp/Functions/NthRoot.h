//
// Created by Al on 7/4/2020.
//

#ifndef HELLOOBOE_NTHROOT_H
#define HELLOOBOE_NTHROOT_H

#include "Function.h"

template <typename T>
struct NthRoot : public Function<T> {
    NthRoot(std::pair<double, double> p0, std::pair<double, double> p1);

    double fun(double x);

private:
    // a*(x^(1.0/b)
    double a;
    double b;
};

#endif //HELLOOBOE_NTHROOT_H
