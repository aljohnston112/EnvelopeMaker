//
// Created by Al on 6/29/2020.
//

#include "AudioStreamCallback.h"
#include <vector>
#include <android/log.h>

void AudioStreamCallback::onErrorBeforeClose(oboe::AudioStream *, oboe::Result) {

}

void AudioStreamCallback::onErrorAfterClose(oboe::AudioStream *, oboe::Result) {

}

oboe::DataCallbackResult
AudioStreamCallback::onAudioReady(oboe::AudioStream *audioStream, void *audioData,
                                  int32_t numFrames) {
    // For production code always check what format
    // the pAudioStream_ has and cast to the appropriate type.
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
        std::vector<float> out = callbackContainer_.get(numFrames);
        if (out.size() != 0) {
            for (int i = 0; (i < numFrames); i++) {
                outputData[i] = out[i];
                empty = false;
            }
        }
    }
    if (empty) {
        (*pAudioStream)->stop();
    }
    return oboe::DataCallbackResult::Continue;
}

