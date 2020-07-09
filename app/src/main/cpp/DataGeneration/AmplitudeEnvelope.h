//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_AMPLITUDEENVELOPE_H
#define HELLOOBOE_AMPLITUDEENVELOPE_H

#include <vector>
#include "../Functions/Function.h"
#include "Envelope.h"

struct AmplitudeEnvelope : Envelope {
    AmplitudeEnvelope(const std::vector<double> ampsIn) {
        amps = ampsIn;
    };

    std::vector<double> *getAmplitudes() { return &amps; };

private:
    std::vector<double> amps;
};

#endif //HELLOOBOE_AMPLITUDEENVELOPE_H
