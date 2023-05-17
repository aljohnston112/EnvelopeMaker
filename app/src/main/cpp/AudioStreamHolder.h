#ifndef HELLOOBOE_AUDIOSTREAMHOLDER_H
#define HELLOOBOE_AUDIOSTREAMHOLDER_H

#include <oboe/Oboe.h>
#include "CallBackHandling/AudioStreamCallback.h"
#include "Functions/Quadratic.h"
#include "Functions/NthRoot.h"
#include "Functions/Exponential.h"
#include "Functions/Logarithm.h"
#include "Functions/Sine.h"
#include "Functions/Linear.h"
#include "DataGeneration/WaveMaker.h"

struct AudioStreamHolder {

    AudioStreamHolder(int sampleRate, int numChannels);




    ~AudioStreamHolder();

    void startStream() {
        pAudioStream_->start();
    }

    void stopStream() {
        pAudioStream_->stop();
    }

    template<typename T>
    WaveMaker<T> &getWaveMaker() {
        return waveMaker_;
    }

    template<typename T>
    T getMinAmp() {
        return waveMaker_.getMinAmp();

        throw std::logic_error("Template argument used for getMinAmp() must be a float or int16_t");
    }

    template<typename T>
    T getMaxAmp() {
        return waveMaker_.getMaxAmp();

        throw std::logic_error("Template argument used for getMaxAmp() must be a float or int16_t");
    }

    template<typename T>
    T getMinFreq() {
        return waveMaker_.getMinFreq();

        throw std::logic_error(
                "Template argument used for getMinFreq() must be a float or int16_t");
    }

    template<typename T>
    T getMaxFreq() {
        return waveMaker_.getMaxFreq();

        throw std::logic_error(
                "Template argument used for getMaxFreq() must be a float or int16_t");
    }

    template<typename T>
    void loadData() {
        audioStreamCallback_.insert(
                waveMaker_.make(sampleRate));

    };

private:
    WaveMaker<float> waveMaker_;
    std::shared_ptr<oboe::AudioStream> pAudioStream_;
    AudioStreamCallback audioStreamCallback_;

    void initAudioStream();
};

#endif //HELLOOBOE_AUDIOSTREAMHOLDER_H
