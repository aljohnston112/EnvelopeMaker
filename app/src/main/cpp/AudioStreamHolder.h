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
#include "DataGeneration/WaveMaker.h"

struct AudioStreamHolder {

    AudioStreamHolder();

    ~AudioStreamHolder();

    bool audioDataIsFloat() { return isFloatData; };

    float getSampleRate(){
        return sampleRate;
    }

    template <typename T>
    WaveMaker<T>& getWaveMaker() {
        if constexpr(std::is_same<T, float>::value) {
            return waveMakerF;
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI;
        } else{
            return nullptr;
        }
    }

    template <typename T>
    T getMinAmp() {
        if constexpr(std::is_same<T, float>::value) {
            waveMakerF.getMinAmp();
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI.getMinAmp();
        } else{
            return nullptr;
        }
    }

    template <typename T>
    T getMaxAmp() {
        if constexpr(std::is_same<T, float>::value) {
            waveMakerF.getMaxAmp();
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI.getMaxAmp();
        } else{
            return nullptr;
        }
    }

    template <typename T>
    T getMinFreq() {
        if constexpr(std::is_same<T, float>::value) {
            waveMakerF.getMinFreq();
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI.getMinFreq();
        } else{
            return nullptr;
        }
    }

    template <typename T>
    T getMaxFreq() {
        if constexpr(std::is_same<T, float>::value) {
            waveMakerF.getMaxFreq();
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI.getMaxFreq();
        } else{
            return nullptr;
        }
    }

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
        FrequencyEnvelope<T> frequencyEnvelope{exponential, exponential.Function::fun<T>(x0, x1, points)};

        x0 = 0.0;
        y0 = 1.0;
        x1 = 8.0;
        y1 = 0.0;

        Linear linear{std::pair{x0, y0}, std::pair{x1, y1}};
        AmplitudeEnvelope<T> amplitudeEnvelope{linear, linear.Function::fun<T>(x0, x1, points)};

        auto data = make<T>(amplitudeEnvelope, frequencyEnvelope, radians, samplesPerSecond);
        if constexpr(std::is_same<T, float>::value) {
            audioStreamCallbackSub.insertF(
                    make<T>(amplitudeEnvelope, frequencyEnvelope, radians, samplesPerSecond));
        } else if constexpr(std::is_same<T, int16_t>::value) {
            audioStreamCallbackSub.insertI(
                    make<T>(amplitudeEnvelope, frequencyEnvelope, radians, samplesPerSecond));
        }
        return data;
    };

private:
    // Initialized when JNI calls init
    oboe::ManagedStream managedStream;
    bool isFloatData;
    float sampleRate;

    // Not initialized
    AudioStreamCallbackSub audioStreamCallbackSub;
    WaveMaker<float> waveMakerF;
    WaveMaker<int16_t> waveMakerI;

    void initAudioStream();
};

#endif //HELLOOBOE_AUDIOSTREAMHOLDER_H
