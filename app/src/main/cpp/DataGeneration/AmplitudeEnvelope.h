//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_AMPLITUDEENVELOPE_H
#define HELLOOBOE_AMPLITUDEENVELOPE_H

#include <vector>
#include "../Functions/Function.h"
#include "Envelope.h"
#include <algorithm>

template <typename T>
struct AmplitudeEnvelope : Envelope {

    AmplitudeEnvelope<T>(Function *ffunction, double length, float sampleRate) {
        amps = ffunction->Function::fun<T>(0, 1, (length * sampleRate));
        function = ffunction;
    };

    AmplitudeEnvelope<T>(AmplitudeEnvelope<T> const &amplitudeEnvelope) : amps(
            amplitudeEnvelope.getAmplitudes()) {};

    AmplitudeEnvelope<T> &operator=(AmplitudeEnvelope<T> amplitudeEnvelope) {
        std::swap(amps, *amplitudeEnvelope.getAmplitudes());
        return *this;
    };

    const std::vector<T> getAmplitudes() const { return amps; };

    std::vector<T> *getAmplitudes() { return &amps; };

    double getTime(int samplesPerSecond) {
        return ((double) amps.size() / (double) samplesPerSecond);
    }


private:
    std::vector<T> amps;
};

#endif //HELLOOBOE_AMPLITUDEENVELOPE_H
