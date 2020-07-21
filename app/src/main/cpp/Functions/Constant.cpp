//
// Created by Al on 7/3/2020.
//

#include "Constant.h"

Constant::Constant(double cnst) {
    c = cnst;
    addPoint(std::pair{0, cnst});
    setMin(cnst);
    setMax(cnst);
}

double Constant::fun(double x) {
    return c;
}

