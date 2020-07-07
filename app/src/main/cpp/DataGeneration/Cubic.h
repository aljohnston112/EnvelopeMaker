//
// Created by Al on 7/4/2020.
//

#ifndef HELLOOBOE_CUBIC_H
#define HELLOOBOE_CUBIC_H

#include "Function.h"

struct Cubic : public Function {
    Cubic(std::pair<double, double> p0, std::pair<double, double> vertex, std::pair<double, double> vertex2);
    double f(double x);

private:
    //a(x^3)+b(x^2)+cx+d
    double a;
    double b;
    double c;
    double d;
};

#endif //HELLOOBOE_CUBIC_H
