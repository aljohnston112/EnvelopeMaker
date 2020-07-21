//
// Created by Al on 7/5/2020.
//

#ifndef HELLOOBOE_POWER_H
#define HELLOOBOE_POWER_H

#include "Function.h"

struct Power : public Function {
    Power(std::pair<double, double> p0, std::pair<double, double> p1);

    double fun(double x);

private:
    // a^(b*x)
    double a;
    double b;
};

#endif //HELLOOBOE_POWER_H
