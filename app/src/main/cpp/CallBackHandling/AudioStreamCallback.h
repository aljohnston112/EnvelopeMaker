//
// Created by Al on 6/29/2020.
//

#ifndef HELLOOBOE_AUDIOSTREAMCALLBACKSUB_H
#define HELLOOBOE_AUDIOSTREAMCALLBACKSUB_H

#include <oboe/Oboe.h>
#include <vector>
#include "CallbackContainer.h"
#include "CallbackContainerI.h"

struct AudioStreamCallback : public oboe::AudioStreamCallback {

    AudioStreamCallback() : callbackContainer_() {};



    ~AudioStreamCallback() {};

    oboe::DataCallbackResult
    onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) override;

    void onErrorBeforeClose(oboe::AudioStream *, oboe::Result) override;

    void onErrorAfterClose(oboe::AudioStream *, oboe::Result) override;

    template<typename T>
    void insert(std::vector<T> data) {
        callbackContainer_.insert(data);
    }

    void insert(std::vector<int16_t> data) {

    }

private:
    CallbackContainer callbackContainer_;
};

#endif //HELLOOBOE_AUDIOSTREAMCALLBACKSUB_H
