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
    AudioStreamCallbackSub() {};

    ~AudioStreamCallbackSub() {};

    oboe::DataCallbackResult
    onAudioReady(oboe::AudioStream *audioStream, void *audioData, int32_t numFrames) override;

    void onErrorBeforeClose(oboe::AudioStream *, oboe::Result) override;

    void onErrorAfterClose(oboe::AudioStream *, oboe::Result) override;

    CallbackContainerF &getCcf();

    CallbackContainerI &getCci();

private:
    CallbackContainerF ccf{};
    CallbackContainerI cci{};
};

#endif //HELLOOBOE_AUDIOSTREAMCALLBACKSUB_H
