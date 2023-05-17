#include "DataGeneration/SineWaveMaker.h"
#include "AudioStreamHolder.h"
#include "Functions/Linear.h"
#include "Functions/Quadratic.h"
#include "Functions/Constant.h"
#include <string>
#include <unistd.h>
#include <android/log.h>
#include <utility>
#include <vector>
#include <cmath>

oboe::Result tryStartStream(
        std::shared_ptr<oboe::AudioStream> pAudioStream,
        AudioStreamCallback audioStreamCallback
);

AudioStreamHolder::AudioStreamHolder(
        int sampleRate,
        int numChannels
        ) :
        audioStreamCallback_(),
        waveMaker_(sampleRate, numChannels) {
    initAudioStream();
}

void AudioStreamHolder::initAudioStream() {
    oboe::Result result = tryStartStream(
            pAudioStream_,
            audioStreamCallback_
    );
    if (result != oboe::Result::OK) {
        __android_log_print(
                ANDROID_LOG_ERROR,
                "TRACKERS",
                "%s", "Failed to create pAudioStream_"
        );
    } else {
        __android_log_print(
                ANDROID_LOG_DEBUG,
                "TRACKERS",
                "%s", "Created pAudioStream_"
        );
    }
}




oboe::Result tryStartStream(
        std::shared_ptr<oboe::AudioStream> pAudioStream,
        AudioStreamCallback audioStreamCallback
) {
    oboe::AudioStreamBuilder builder{};
    builder.setDirection(oboe::Direction::Output);
    builder.setSharingMode(oboe::SharingMode::Shared);
    builder.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    builder.setFormat(oboe::AudioFormat::Float);
    builder.setCallback(&audioStreamCallback);
    return builder.openStream(pAudioStream);
}

AudioStreamHolder::~AudioStreamHolder() {
    pAudioStream_->requestStop();
    pAudioStream_->close();
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Destroyed pAudioStream_");
}
