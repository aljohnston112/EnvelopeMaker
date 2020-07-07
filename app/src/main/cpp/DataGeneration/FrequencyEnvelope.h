//
// Created by Al on 7/5/2020.
//

#ifndef HELLOOBOE_FREQUENCYENVELOPE_H
#define HELLOOBOE_FREQUENCYENVELOPE_H

#include <vector>

struct FrequencyEnvelope {
    FrequencyEnvelope(const std::vector<double> freqsIn) {
        freqs = freqsIn;
    };

    std::vector<double> *getFrequencies() { return &freqs; };
private:
    std::vector<double> freqs;
};

#endif //HELLOOBOE_FREQUENCYENVELOPE_H
