//
// Created by Al on 6/29/2020.
//

#ifndef HELLOOBOE_AUDIOSTREAMCALLBACKSUB_H
#define HELLOOBOE_AUDIOSTREAMCALLBACKSUB_H

#include <oboe/Oboe.h>
#include <vector>
#include "CallbackContainerF.h"
#include "CallbackContainerI.h"

struct AudioStreamCallbackSub : public oboe::AudioStreamCallback {
    AudioStreamCallbackSub() : callbackContainerF(), callbackContainerI() {
    };

    ~AudioStreamCallbackSub() {};

    void setStream(oboe::ManagedStream *mmanagedStream) {
        managedStream = mmanagedStream;
    }

    oboe::DataCallbackResult
    onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) override;

    void onErrorBeforeClose(oboe::AudioStream *, oboe::Result) override;

    void onErrorAfterClose(oboe::AudioStream *, oboe::Result) override;

    template<typename T>
    void insert(std::vector<T> data) {
        if constexpr(std::is_same<T, float>::value) {
            callbackContainerF.insert(data);
        } else if constexpr(std::is_same<T, int16_t>::value) {
            callbackContainerI.insert(data);
        }
    }

    void insert(std::vector<int16_t> data) {

    }

private:
    CallbackContainerF callbackContainerF;
    CallbackContainerI callbackContainerI;
    oboe::ManagedStream *managedStream;
};

#endif //HELLOOBOE_AUDIOSTREAMCALLBACKSUB_H
