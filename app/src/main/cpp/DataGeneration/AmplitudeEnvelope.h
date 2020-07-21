//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_AMPLITUDEENVELOPE_H
#define HELLOOBOE_AMPLITUDEENVELOPE_H

#include <vector>
#include "../Functions/Function.h"
#include "Envelope.h"

template <typename T>
struct AmplitudeEnvelope : Envelope {

    AmplitudeEnvelope(Function& ffunction, const std::vector<T>& ampsIn) {
        amps = ampsIn;
        function = &ffunction;
    };

    std::vector<T> *getAmplitudes() { return &amps; };

private:
    std::vector<T> amps;
};

#endif //HELLOOBOE_AMPLITUDEENVELOPE_H
