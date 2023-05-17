#ifndef HELLOOBOE_WAVEMAKER_H
#define HELLOOBOE_WAVEMAKER_H

#include "SineWaveMaker.h"
#include <list>

template<typename T>
struct WaveMaker {

    WaveMaker(
            int sampleRate,
            int numChannels
    ) : sampleRate_(sampleRate),
        numChannels_(numChannels),
        amplitudeFunctions_(),
        frequencyFunctions_() {};

    void insert_amp(Function<T> *ae, int index) {
        if (index < 0 || index > amplitudeFunctions_.size()) {
            throw std::range_error("index passed to insert() was out of bounds");
        }
        auto iterator = amplitudeFunctions_.begin();
        for (int i = 0; i < index; i++) {
            iterator++;
        }
        if (iterator != amplitudeFunctions_.end()) {
            amplitudeFunctions_.erase(iterator);
        }
        amplitudeFunctions_.insert(iterator, ae);
        make(sampleRate_);
    };

    void insert_freq(Function<T> *fe, int index) {
        if (index < 0 || index > frequencyFunctions_.size()) {
            throw std::range_error("index passed to insert() was out of bounds");
        }
        auto iterator = frequencyFunctions_.begin();
        for (int i = 0; i < index; i++) {
            iterator++;
        }
        if (iterator != frequencyFunctions_.end()) {
            frequencyFunctions_.erase(iterator);
        }
        frequencyFunctions_.insert(iterator, fe);
        make(sampleRate_);
    };

    void erase_amp(typename std::list<Function<T>>::iterator index) {
        amplitudeFunctions_.erase(index);
        make(sampleRate_);
    };

    void erase_freq(typename std::list<Function<T>>::iterator index) {
        frequencyFunctions_.erase(index);
        make(sampleRate_);
    };


    double getMaxAmp() {
        std::vector<double> ampsList{};
        for (auto a: amplitudeFunctions_) {
            ampsList.push_back((*a).getMax());
        }
        return (ampsList.begin() != ampsList.end()) ?
               *(std::max_element(ampsList.begin(), ampsList.end())) : 0;
    };

    double getMinAmp() {
        std::vector<double> ampsList{};
        for (auto a: amplitudeFunctions_) {
            ampsList.push_back((*a).getMin());
        }
        return (ampsList.begin() != ampsList.end()) ?
               *(std::min_element(ampsList.begin(), ampsList.end())) : 0;
    };

    double getMaxFreq() {
        std::vector<double> freqsList{};
        for (auto f: frequencyFunctions_) {
            freqsList.push_back((*f).getMax());
        }
        return (freqsList.begin() != freqsList.end()) ?
               *(std::max_element(freqsList.begin(), freqsList.end())) : 0;
    };

    double getMinFreq() {
        std::vector<double> freqsList{};
        for (auto f: frequencyFunctions_) {
            freqsList.push_back((*f).getMin());
        }
        return (freqsList.begin() != freqsList.end()) ?
               *(std::min_element(freqsList.begin(), freqsList.end())) : 0;
    };

    std::vector<T> *getData() { return &data; };

    /**
    AmplitudeEnvelope<T> *getAmpEnv(int col) {
        auto out = amplitudeFunctions_.begin();
        for (int i = 0; i < col; i++) {
            out++;
        }
        return (*out);
    };

    FrequencyEnvelope<T> *getFreqEnv(int col) {
        auto out = frequencyFunctions_.begin();
        for (int i = 0; i < col; i++) {
            out++;
        }
        return (*out);
    };

    double getAmpTime(int index, int samplesPerSecond) {
        auto out = amplitudeFunctions_.begin();
        for (int i = 0; i < index; i++) {
            out++;
        }
        return (*out)->getTime(samplesPerSecond);
    }

    double getFreqTime(int index, int samplesPerSecond) {
        auto out = frequencyFunctions_.begin();
        for (int i = 0; i < index; i++) {
            out++;
        }
        return (*out)->getTime(samplesPerSecond);
    }
     */

    void make(int samplesPerSecond) {
        double radians = 0;
        auto amp = amplitudeFunctions_.begin();
        auto freq = frequencyFunctions_.begin();
        std::vector<T> amplitudes{};
        std::vector<T> frequencies{};
        for (int i = 0; i < amplitudeFunctions_.size(); i++) {
            if ((amp != amplitudeFunctions_.end()) && (*amp)->getData()->size() != 0) {
                amplitudes.insert(amplitudes.end(), (*((*amp)->getData())).begin(),
                                  (*((*amp)->getData())).end());
            }
        }
        for (int i = 0; i < frequencyFunctions_.size(); i++) {
            if ((freq != frequencyFunctions_.end()) && (*freq)->getData()->size() != 0) {
                frequencies.insert(frequencies.end(), (*((*freq)->getData())).begin(),
                                   (*((*freq)->getData())).end());
            }
        }
        if (frequencies.size() > amplitudes.size()) {
            double amplitude = 1;
            int add = frequencies.size() - amplitudes.size();
            for (int i = 0; i < add; i++) {
                amplitudes.push_back(amplitude);
            }
        } else if (frequencies.size() < amplitudes.size()) {
            double frequency = 0;
            int add = amplitudes.size() - frequencies.size();
            for (int i = 0; i < add; i++) {
                frequencies.push_back(frequency);
            }
        }
        data = SINEWAVEMAKER_H_::make < T > (amplitudes, frequencies, radians, samplesPerSecond);
    }

private:
    int sampleRate_;
    int numChannels_;

    std::list<std::shared_ptr<Function<T>>> amplitudeFunctions_;
    std::list<std::shared_ptr<Function<T>>> frequencyFunctions_;

    std::vector<T> data;

};

#endif //HELLOOBOE_WAVEMAKER_H
