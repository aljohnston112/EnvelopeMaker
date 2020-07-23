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

    float getSampleRate() {
        return sampleRate;
    }

    void startStream() {
        managedStream->start();
    }

    void stopStream() {
        managedStream->stop();
    }

    template<typename T>
    WaveMaker<T> &getWaveMaker() {
        if constexpr(std::is_same<T, float>::value) {
            return waveMakerF;
        } else if constexpr(std::is_same<T, int16_t>::value) {
            return waveMakerI;
        } else {
            return nullptr;
        }
    }

    template <typename T>
    T getMinAmp() {
        if constexpr(std::is_same<T, float>::value) {
            return waveMakerF.getMinAmp();
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI.getMinAmp();
        }
            return NULL;
    }

    template <typename T>
    T getMaxAmp() {
        if constexpr(std::is_same<T, float>::value) {
            return waveMakerF.getMaxAmp();
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI.getMaxAmp();
        }
        return NULL;
    }

    template <typename T>
    T getMinFreq() {
        if constexpr(std::is_same<T, float>::value) {
            return waveMakerF.getMinFreq();
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI.getMinFreq();
        }
        return NULL;
    }

    template <typename T>
    T getMaxFreq() {
        if constexpr(std::is_same<T, float>::value) {
            return waveMakerF.getMaxFreq();
        } else  if constexpr(std::is_same<T, int16_t >::value){
            return waveMakerI.getMaxFreq();
        }
        return NULL;
    }

    template<typename T>
    void loadData() {
        if constexpr(std::is_same<T, float>::value) {
            audioStreamCallbackSub.insertF(
                    waveMakerF.make(sampleRate));
        } else if constexpr(std::is_same<T, int16_t>::value) {
            audioStreamCallbackSub.insertI(
                    waveMakerI.make(sampleRate));
        }
    };

private:
    // Initialized when JNI calls init
    oboe::ManagedStream managedStream;
    bool isFloatData;
    float sampleRate;

    // Initialized in Constructor
    AudioStreamCallbackSub audioStreamCallbackSub;
    WaveMaker<float> waveMakerF;
    WaveMaker<int16_t> waveMakerI;

    void initAudioStream();
};

#endif //HELLOOBOE_AUDIOSTREAMHOLDER_H
