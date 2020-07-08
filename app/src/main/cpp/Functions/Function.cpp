//
// Created by Al on 7/3/2020.
//

#include "Function.h"

std::vector<double> Function::f(double x0, double x1, int points) {
    double dx = (x1 - x0) / (points - 1);
    std::vector<double> out{};
    for (int i = 0; i < points; i++) {
        out.push_back(f(x0 + (i * dx)));
    }
    return out;
}

