//
// Created by Al on 7/7/2020.
//

#ifndef HELLOOBOE_WAVEMAKER_H
#define HELLOOBOE_WAVEMAKER_H

#include "AmplitudeEnvelope.h"
#include "FrequencyEnvelope.h"
#include "SineWave.h"

#include <list>

struct WaveMaker {

    void push_back(AmplitudeEnvelope ae) { amps.push_back(ae); };

    void push_back(FrequencyEnvelope ae) { freqs.push_back(ae); };

    void erase(std::list<AmplitudeEnvelope>::iterator index) { amps.erase(index); }

    void erase(std::list<FrequencyEnvelope>::iterator index) { freqs.erase(index); }

    template<typename T>
    std::vector<T> make(int index, int samplesPerSecond) {
        double radians = 0;
        std::vector<float> data{};
        auto amp = amps.begin();
        auto freq = freqs.begin();
        double amplitude = 1.0;
        for (int i = 0; i < amps.size(); i++) {
            if (amp->getAmplitudes()->size() != 0) {
                if (freq->getFrequencies()->size() != 0) {
                    data.insert(data.size() - 1,
                                ::make<T>(amp->getAmplitudes(), freq->getFrequencies(), radians,
                                        samplesPerSecond));
                } else {
                    for (int i = 0; i < amp->getAmplitudes()->size(); i++) {
                        data.push_back(0.0);
                    }
                }
            } else {
                amplitude = 1.0;
                if (freq->getFrequencies()->size() != 0) {
                    data.insert(data.size() - 1,
                                ::make<T>(amplitude, freq->getFrequencies(), radians,
                                        samplesPerSecond));
                }
            }
        }
    }

    double getMaxAmp(){
        std::vector<double> ampsList;
        for(auto a : amps){
            ampsList.push_back(a.getMax());
        }
        return *(std::max_element(ampsList.begin(), ampsList.end()));
    };

    double getMaxFreq(){
        std::vector<double> freqsList;
        for(auto f : freqs){
            freqsList.push_back(f.getMax());
        }
        return *(std::max_element(freqsList.begin(), freqsList.end()));
    };

    double getMinAmp(){
        std::vector<double> ampsList;
        for(auto a : amps){
            ampsList.push_back(a.getMin());
        }
        return *(std::min_element(ampsList.begin(), ampsList.end()));
    };

    double getMinFreq(){
        std::vector<double> freqsList;
        for(auto f : freqs){
            freqsList.push_back(f.getMin());
        }
        return *(std::min_element(freqsList.begin(), freqsList.end()));
    };

private:
    std::list<AmplitudeEnvelope> amps{};
    std::list<FrequencyEnvelope> freqs{};
};

#endif //HELLOOBOE_WAVEMAKER_H
