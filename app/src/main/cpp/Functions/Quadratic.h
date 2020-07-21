//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_QUADRATIC_H
#define HELLOOBOE_QUADRATIC_H


#include "Function.h"

struct Quadratic : public Function {
    Quadratic(std::pair<double, double> p0, std::pair<double, double> vertex);

    double fun(double x);

private:
    //a(x^2)+bx+c
    double a;
    double b;
    double c;
};


#endif //HELLOOBOE_QUADRATIC_H
