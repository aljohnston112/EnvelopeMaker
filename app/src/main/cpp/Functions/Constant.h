//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_CONSTANT_H
#define HELLOOBOE_CONSTANT_H

#include "Function.h"

struct Constant : public Function {
    Constant(double cnst);

    double fun(double x);

private:
    double c;
};

#endif //HELLOOBOE_CONSTANT_H
