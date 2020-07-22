//
// Created by Al on 7/7/2020.
//

#ifndef HELLOOBOE_WAVEMAKER_H
#define HELLOOBOE_WAVEMAKER_H

#include "AmplitudeEnvelope.h"
#include "FrequencyEnvelope.h"
#include "SineWaveMaker.h"

#include <list>
#include <android/log.h>

template <typename T>
struct WaveMaker {

    WaveMaker() : amps(), freqs() {};

    void push_back(AmplitudeEnvelope<T> ae) { amps.push_back(ae); };

    void push_back(FrequencyEnvelope<T> fe) { freqs.push_back(fe); };

    void insert(AmplitudeEnvelope<T>& ae, int index) {
        if(index < 0 || index > amps.size()){
            throw std::range_error("index passed to insert() was out of bounds");
        }
        auto iterator = amps.begin();
        for(int i = 0; i < index; i++){
            iterator++;
        }
            amps.insert(iterator, &ae);
    };

    void insert(FrequencyEnvelope<T>& fe, int index) {
        if(index < 0 || index > freqs.size()) {
            throw std::range_error("index passed to insert() was out of bounds");
        }
        auto iterator = freqs.begin();
        for(int i = 0; i < index; i++){
            iterator++;
        }
        freqs.insert(iterator, &fe); };

    void erase(typename std::list<AmplitudeEnvelope<T>>::iterator index) { amps.erase(index); };

    void erase(typename std::list<FrequencyEnvelope<T>>::iterator index) { freqs.erase(index); };

    std::vector<T> make(int samplesPerSecond) {
        double radians = 0;
        std::vector<float> data{};
        auto amp = amps.begin();
        auto freq = freqs.begin();
        double amplitude;
        for (int i = 0; i < amps.size(); i++) {
            if ((*amp)->getAmplitudes()->size() != 0) {
                if ((*freq)->getFrequencies()->size() != 0) {
                    std::vector<T> concat{::make<T>(**amp, **freq, radians,
                            samplesPerSecond)};
                    data.insert(data.end(),
                                concat.begin(), concat.end());
                } else {
                    for (int j = 0; j < (*amp)->getAmplitudes()->size(); j++) {
                        data.push_back(0.0);
                    }
                }
            } else {
                amplitude = 1.0;
                std::vector<T> concat{::make<T>(amplitude, (**freq), radians,
                        samplesPerSecond)};
                if ((*freq)->getFrequencies()->size() != 0) {
                    data.insert(data.end(),
                                concat.begin(), concat.end());
                }
            }
        }
    }

    double getMaxAmp() {
        std::vector<double> ampsList{};
        for (auto a : amps) {
            ampsList.push_back((*a).getMax());
        }
        return *(std::max_element(ampsList.begin(), ampsList.end()));
    };

    double getMaxFreq() {
        std::vector<double> freqsList{};
        for (auto f : freqs) {
            freqsList.push_back((*f).getMax());
        }
        return *(std::max_element(freqsList.begin(), freqsList.end()));
    };

    double getMinAmp() {
        std::vector<double> ampsList{};
        for (auto a : amps) {
            ampsList.push_back((*a).getMin());
        }
        return *(std::min_element(ampsList.begin(), ampsList.end()));
    };

    double getMinFreq() {
        std::vector<double> freqsList{};
        for (auto f : freqs) {
            freqsList.push_back((*f).getMin());
        }
        return *(std::min_element(freqsList.begin(), freqsList.end()));
    };

private:
    std::list<AmplitudeEnvelope<T>*> amps;
    std::list<FrequencyEnvelope<T>*> freqs;
};

#endif //HELLOOBOE_WAVEMAKER_H
