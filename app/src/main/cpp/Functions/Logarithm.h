//
// Created by Al on 7/5/2020.
//

#ifndef HELLOOBOE_LOGARITHM_H
#define HELLOOBOE_LOGARITHM_H

#include "Function.h"

template <typename T>
struct Logarithm : public Function<T> {
    Logarithm(std::pair<double, double> p0, std::pair<double, double> p1);

    double fun(double x);

private:
    // a*ln(x)+b
    double a;
    double b;
};

#endif //HELLOOBOE_LOGARITHM_H
