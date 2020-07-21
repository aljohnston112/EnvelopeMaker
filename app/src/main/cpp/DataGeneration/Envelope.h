//
// Created by Al on 7/9/2020.
//

#ifndef HELLOOBOE_ENVELOPE_H
#define HELLOOBOE_ENVELOPE_H

#include "../Functions/Function.h"

struct Envelope {
    double getMin() { return function->getMin(); };

    double getMax() { return function->getMax(); };

protected:
    Function *function;

};

#endif //HELLOOBOE_ENVELOPE_H
