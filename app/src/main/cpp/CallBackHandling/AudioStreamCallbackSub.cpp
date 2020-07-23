//
// Created by Al on 6/29/2020.
//

#include "AudioStreamCallbackSub.h"
#include <vector>
#include <android/log.h>

void AudioStreamCallbackSub::onErrorBeforeClose(oboe::AudioStream *, oboe::Result) {

}

void AudioStreamCallbackSub::onErrorAfterClose(oboe::AudioStream *, oboe::Result) {

}

oboe::DataCallbackResult
AudioStreamCallbackSub::onAudioReady(oboe::AudioStream *audioStream, void *audioData,
                                     int32_t numFrames) {
    // For production code always check what format
    // the stream has and cast to the appropriate type.
    int bytePerSample = audioStream->getBytesPerSample();
    bool empty = true;
    if (bytePerSample == 2) {
        auto *outputData = static_cast<int16_t *>(audioData);
        std::vector<int16_t> out = callbackContainerI.get(numFrames);
        if (out.size() != 0) {
            for (int i = 0; (i < numFrames); i++) {
                outputData[i] = out[i];
                empty = false;
            }
        }
    } else {
        auto *outputData = static_cast<float *>(audioData);
        std::vector<float> out = callbackContainerF.get(numFrames);
        if (out.size() != 0) {
            for (int i = 0; (i < numFrames); i++) {
                outputData[i] = out[i];
                empty = false;
            }
        }
    }
    if (empty) {
        (*managedStream)->stop();
    }
    return oboe::DataCallbackResult::Continue;
}

