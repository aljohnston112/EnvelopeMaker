//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_LINEAR_H
#define HELLOOBOE_LINEAR_H

#include <utility>
#include "Function.h"

template <typename T>
struct Linear : public Function<T> {
    Linear(std::pair<double, double> p0, std::pair<double, double> p1);

    double fun(double x);

private:
    // ax+b
    double a;
    double b;
};

#endif //HELLOOBOE_LINEAR_H
