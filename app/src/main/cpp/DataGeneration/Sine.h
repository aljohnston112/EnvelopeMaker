//
// Created by Al on 7/5/2020.
//

#ifndef HELLOOBOE_SINE_H
#define HELLOOBOE_SINE_H

#include "Function.h"

struct Sine : public Function {
    Sine(std::pair<double, double> p0, std::pair<double, double> p1);

    double f(double x);

private:
    // a*sin(x)+b
    double a;
    double b;
};

#endif //HELLOOBOE_SINE_H
