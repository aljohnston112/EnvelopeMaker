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
    if (bytePerSample == 2) {
        auto *outputData = static_cast<int16_t *>(audioData);
        std::vector<int16_t> out = cci.get(numFrames);
        for (int i = 0; i < numFrames; i++) {
            outputData[i] = out[i];
        }
    } else {
        auto *outputData = static_cast<float *>(audioData);
        std::vector<float> out = ccf.get(numFrames);
        for (int i = 0; i < numFrames; i++) {
            outputData[i] = out[i];
        }
    }
    return oboe::DataCallbackResult::Continue;
}

