//
// Created by Al on 7/5/2020.
//

#ifndef HELLOOBOE_FREQUENCYENVELOPE_H
#define HELLOOBOE_FREQUENCYENVELOPE_H

#include <vector>
#include "Envelope.h"

template <typename T>
struct FrequencyEnvelope : Envelope {
    FrequencyEnvelope(Function& ffunction, const std::vector<T> freqsIn) {
        freqs = freqsIn;
        function = &ffunction;
    };

    std::vector<T> *getFrequencies() { return &freqs; };
private:
    std::vector<T> freqs;
};

#endif //HELLOOBOE_FREQUENCYENVELOPE_H
