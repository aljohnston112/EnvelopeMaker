//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_FUNCTION_H
#define HELLOOBOE_FUNCTION_H

#include <vector>

struct Function {
    std::vector<double> f(double x0, double x1, int points);

    virtual double f(double x) = 0;
};

#endif //HELLOOBOE_FUNCTION_H
