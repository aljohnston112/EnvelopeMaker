//
// Created by Al on 7/5/2020.
//

#ifndef HELLOOBOE_FREQUENCYENVELOPE_H
#define HELLOOBOE_FREQUENCYENVELOPE_H

#include <vector>
#include "Envelope.h"

template <typename T>
struct FrequencyEnvelope : Envelope {

    FrequencyEnvelope<T>(Function *ffunction, double length, float sampleRate) {
        freqs = ffunction->Function::fun<T>(0, 1, (length * sampleRate));
        function = ffunction;
    };

    FrequencyEnvelope<T>(FrequencyEnvelope<T> const &frequencyEnvelope) : freqs(
            frequencyEnvelope.getFrequencies()) {};

    FrequencyEnvelope<T> &operator=(FrequencyEnvelope<T> frequencyEnvelope) {
        std::swap(freqs, *frequencyEnvelope.getFrequencies());
        return *this;
    }

    const std::vector<T> getFrequencies() const { return freqs; };

    std::vector<T> *getFrequencies() { return &freqs; };

private:
    std::vector<T> freqs;
};

#endif //HELLOOBOE_FREQUENCYENVELOPE_H
