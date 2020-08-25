//
// Created by Al on 7/4/2020.
//

#ifndef HELLOOBOE_EXPONENTIAL_H
#define HELLOOBOE_EXPONENTIAL_H

#include "Function.h"

template <typename T>
struct Exponential : public Function<T> {
    Exponential(std::pair<double, double> p0, std::pair<double, double> p1);

    double fun(double x);

private:
    // a*(e^(x))+b
    double a;
    double b;

};

#endif //HELLOOBOE_EXPONENTIAL_H
