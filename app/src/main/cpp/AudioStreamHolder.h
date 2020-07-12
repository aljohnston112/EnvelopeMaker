//
// Created by Al on 6/29/2020.
//

#ifndef HELLOOBOE_AUDIOSTREAMHOLDER_H
#define HELLOOBOE_AUDIOSTREAMHOLDER_H

#include <oboe/Oboe.h>
#include "CallBackHandling/AudioStreamCallbackSub.h"
#include "Functions/Quadratic.h"
#include "Functions/NthRoot.h"
#include "Functions/Exponential.h"
#include "Functions/Logarithm.h"
#include "Functions/Sine.h"
#include "Functions/Linear.h"

struct AudioStreamHolder {

    double minA;

    double maxA;

    double minF;

    double maxF;

    AudioStreamHolder();

    ~AudioStreamHolder();

    void requestStopStream() {
        managedStream->requestStop();
    }

    void closeStream(){
        managedStream->close();
    }

    bool takesFloat() { return isFloat; };

    template<typename T>
    std::vector<T> LoadData() {
        double magnitude = 0.5;
        double frequency = 444.0;
        double radians = 0.0;
        double seconds = 2.0;
        int samplesPerSecond = managedStream->getSampleRate();
        int points = length(seconds, samplesPerSecond);
        double x0 = 0.0;
        double y0 = 222.0;
        double x1 = 8.0;
        double y1 = 444.0;

        Exponential exponential{std::pair{x0, y0}, std::pair{x1, y1}};
        FrequencyEnvelope frequencyEnvelope{exponential.Function::f(x0, x1, points)};

        x0 = 0.0;
        y0 = 1.0;
        x1 = 8.0;
        y1 = 0.0;

        Linear linear{std::pair{x0, y0}, std::pair{x1, y1}};
        AmplitudeEnvelope amplitudeEnvelope{linear.Function::f(x0, x1, points)};

        auto data = make<T>(amplitudeEnvelope, frequencyEnvelope, radians, samplesPerSecond);
        if (std::is_same<T, float>::value) {
            callback.insertF(
                    make<float>(amplitudeEnvelope, frequencyEnvelope, radians, samplesPerSecond));
        } else if (std::is_same<T, int16_t>::value) {
            callback.insertI(
                    make<int16_t>(amplitudeEnvelope, frequencyEnvelope, radians, samplesPerSecond));
        }
        return data;
    };

private:
    oboe::ManagedStream managedStream;
    AudioStreamCallbackSub callback;
    bool isFloat;

    void initAudioStream();
};

#endif //HELLOOBOE_AUDIOSTREAMHOLDER_H
