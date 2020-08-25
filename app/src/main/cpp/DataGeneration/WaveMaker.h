//
// Created by Al on 7/7/2020.
//

#ifndef HELLOOBOE_WAVEMAKER_H
#define HELLOOBOE_WAVEMAKER_H

#include "SineWaveMaker.h"
#include <list>

template <typename T>
struct WaveMaker {

    WaveMaker(int sampleRate) : amps(), freqs(), msampleRate(sampleRate) {};

    void insert_amp(Function<T> *ae, int index) {
        if (index < 0 || index > amps.size()) {
            throw std::range_error("index passed to insert() was out of bounds");
        }
        auto iterator = amps.begin();
        for (int i = 0; i < index; i++) {
            iterator++;
        }
        if(iterator != amps.end()) {
            amps.erase(iterator);
        }
        amps.insert(iterator, ae);
        make(msampleRate);
    };

    void insert_freq(Function<T> *fe, int index) {
        if (index < 0 || index > freqs.size()) {
            throw std::range_error("index passed to insert() was out of bounds");
        }
        auto iterator = freqs.begin();
        for (int i = 0; i < index; i++) {
            iterator++;
        }
        if(iterator != freqs.end()) {
            freqs.erase(iterator);
        }
        freqs.insert(iterator, fe);
        make(msampleRate);
    };

    void erase_amp(typename std::list<Function<T>>::iterator index) { amps.erase(index); make(msampleRate);};

    void erase_freq(typename std::list<Function<T>>::iterator index) { freqs.erase(index); make(msampleRate);};



    double getMaxAmp() {
        std::vector<double> ampsList{};
        for (auto a : amps) {
            ampsList.push_back((*a).getMax());
        }
        return (ampsList.begin() != ampsList.end()) ?
               *(std::max_element(ampsList.begin(), ampsList.end())) : 0;
    };

    double getMinAmp() {
        std::vector<double> ampsList{};
        for (auto a : amps) {
            ampsList.push_back((*a).getMin());
        }
        return (ampsList.begin() != ampsList.end()) ?
               *(std::min_element(ampsList.begin(), ampsList.end())) : 0;
    };

    double getMaxFreq() {
        std::vector<double> freqsList{};
        for (auto f : freqs) {
            freqsList.push_back((*f).getMax());
        }
        return (freqsList.begin() != freqsList.end()) ?
               *(std::max_element(freqsList.begin(), freqsList.end())) : 0;
    };

    double getMinFreq() {
        std::vector<double> freqsList{};
        for (auto f : freqs) {
            freqsList.push_back((*f).getMin());
        }
        return (freqsList.begin() != freqsList.end()) ?
               *(std::min_element(freqsList.begin(), freqsList.end())) : 0;
    };

    std::vector<T>* getData(){return &data;};

    /**
    AmplitudeEnvelope<T> *getAmpEnv(int col) {
        auto out = amps.begin();
        for (int i = 0; i < col; i++) {
            out++;
        }
        return (*out);
    };

    FrequencyEnvelope<T> *getFreqEnv(int col) {
        auto out = freqs.begin();
        for (int i = 0; i < col; i++) {
            out++;
        }
        return (*out);
    };

    double getAmpTime(int index, int samplesPerSecond) {
        auto out = amps.begin();
        for (int i = 0; i < index; i++) {
            out++;
        }
        return (*out)->getTime(samplesPerSecond);
    }

    double getFreqTime(int index, int samplesPerSecond) {
        auto out = freqs.begin();
        for (int i = 0; i < index; i++) {
            out++;
        }
        return (*out)->getTime(samplesPerSecond);
    }
     */

private:
    std::list<Function<T> *> amps;
    std::list<Function<T> *> freqs;

    std::vector<T> data;
    int msampleRate;

    void make(int samplesPerSecond) {
        double radians = 0;
        auto amp = amps.begin();
        auto freq = freqs.begin();
        std::vector<T> amplitudes{};
        std::vector<T> frequencies{};
        for (int i = 0; i < amps.size(); i++) {
            if ((amp != amps.end()) && (*amp)->getData()->size() != 0) {
                amplitudes.insert(amplitudes.end(), (*((*amp)->getData())).begin(),
                                  (*((*amp)->getData())).end());
            }
        }
        for (int i = 0; i < freqs.size(); i++) {
            if ((freq != freqs.end()) && (*freq)->getData()->size() != 0) {
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

};

#endif //HELLOOBOE_WAVEMAKER_H
