//
// Created by Al on 7/4/2020.
//

#ifndef HELLOOBOE_NTHROOT_H
#define HELLOOBOE_NTHROOT_H

#include "Function.h"

struct NthRoot : public Function {
    NthRoot(std::pair<double, double> p0, std::pair<double, double> p1);

    double f(double x);

private:
    // a*(x^(1.0/b)
    double a;
    double b;
};

#endif //HELLOOBOE_NTHROOT_H
