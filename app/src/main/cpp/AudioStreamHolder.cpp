//
// Created by Al on 6/29/2020.
//
#include "DataGeneration/SineWaveMaker.h"
#include "AudioStreamHolder.h"
#include "Functions/Linear.h"
#include "Functions/Quadratic.h"
#include "Functions/Constant.h"
#include <jni.h>
#include <string>
#include <unistd.h>
#include <android/log.h>
#include <utility>
#include <vector>
#include <cmath>

AudioStreamHolder *audioStreamHolder;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_NativeMethods_createStream(JNIEnv *env, jclass thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Creating stream");
    audioStreamHolder = new AudioStreamHolder();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_NativeMethods_destroyStream(JNIEnv *env, jclass thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Destroying stream");
    delete (audioStreamHolder);
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getMinAmp(JNIEnv *env, jclass thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "min amp");
    if (audioStreamHolder->audioDataIsFloat()) {
        return audioStreamHolder->getMinAmp<float>();
    } else {
        return audioStreamHolder->getMinAmp<int16_t>();
    }
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getMaxAmp(JNIEnv *env, jclass thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "max amp");
    if (audioStreamHolder->audioDataIsFloat()) {
        return audioStreamHolder->getMaxAmp<float>();
    } else {
        return audioStreamHolder->getMaxAmp<int16_t>();
    }
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getMinFreq(JNIEnv *env, jclass thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "min freq");
    if (audioStreamHolder->audioDataIsFloat()) {
        return audioStreamHolder->getMinFreq<float>();
    } else {
        return audioStreamHolder->getMinFreq<int16_t>();
    }
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getMaxFreq(JNIEnv *env, jclass thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "max freq");
    if (audioStreamHolder->audioDataIsFloat()) {
        return audioStreamHolder->getMaxFreq<float>();
    } else {
        return audioStreamHolder->getMaxFreq<int16_t>();
    }
}

AudioStreamHolder::AudioStreamHolder() : audioStreamCallbackSub(),
                                         waveMakerF(),
                                         waveMakerI() {
    initAudioStream();
}

AudioStreamHolder::~AudioStreamHolder() {
    managedStream->requestStop();
    managedStream->close();
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Destroyed stream");
}

void AudioStreamHolder::initAudioStream() {
    oboe::AudioStreamBuilder builder{};
    builder.setDirection(oboe::Direction::Output);
    builder.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    builder.setSharingMode(oboe::SharingMode::Exclusive);
    builder.setFormat(oboe::AudioFormat::Float);
    builder.setChannelCount(oboe::ChannelCount::Mono);
    builder.setCallback(&audioStreamCallbackSub);
    oboe::Result result = builder.openManagedStream(managedStream);
    if (result != oboe::Result::OK) {
        __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Failed to create stream");
    } else {
        __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Created stream");
    }
    int bytePerSample = managedStream->getBytesPerSample();
    isFloatData = bytePerSample != 2;
    sampleRate = managedStream->getSampleRate();
    this->audioStreamCallbackSub.setStream(&managedStream);
}

extern "C"
JNIEXPORT jfloatArray JNICALL
Java_com_example_hellooboe_NativeMethods_loadConstant(JNIEnv *env, jclass clazz, jdouble start,
                                                      jdouble length, jint row,
                                                      jint col, jint width) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Loading constant");
    jfloatArray outData;
    std::vector<float> *data;
    if (audioStreamHolder->audioDataIsFloat()) {
        if (row == 0) {
            audioStreamHolder->getWaveMaker<float>().insert(
                    new AmplitudeEnvelope<float>{new Constant{start, length}, length,
                                                 audioStreamHolder->getSampleRate()}, col);
            data = audioStreamHolder->getWaveMaker<float>().getAmpEnv(col)->getAmplitudes();
        } else if (row == 1) {
            audioStreamHolder->getWaveMaker<float>().insert(
                    new FrequencyEnvelope<float>{new Constant{start, length}, length,
                                                 audioStreamHolder->getSampleRate()}, col);
            data = audioStreamHolder->getWaveMaker<float>().getFreqEnv(col)->getFrequencies();
        }
        int skip = std::floor((double)data->size() / (double)width);
        std::vector<float> reduced(width);
        for (int i = 0; i < reduced.size(); i++) {
            reduced[i] = (*data)[i * skip];
        }
        outData = env->NewFloatArray(reduced.size());
        jsize size = env->GetArrayLength(outData);
        env->SetFloatArrayRegion(outData, 0, size, reduced.data());
    } else {
        // TODO
    }
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Loaded constant");
    return outData;
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_hellooboe_NativeMethods_audioDataIsFloat(JNIEnv *env, jclass clazz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "audioDataIsFloat");
    return audioStreamHolder->audioDataIsFloat();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_NativeMethods_makeSound(JNIEnv *env, jclass clazz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Making sound");
    if (audioStreamHolder->audioDataIsFloat()) {
        audioStreamHolder->loadData<float>();
    } else {
        audioStreamHolder->loadData<int16_t>();
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_NativeMethods_startStream(JNIEnv *env, jclass clazz) {
    audioStreamHolder->startStream();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_NativeMethods_stopStream(JNIEnv *env, jclass clazz) {
    audioStreamHolder->stopStream();
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getAmpTime(JNIEnv *env, jclass clazz, jint index,
                                                    jint samplesPerSecond) {
    if (audioStreamHolder->audioDataIsFloat()) {
        return audioStreamHolder->getWaveMaker<float>().getAmpTime(index, samplesPerSecond);
    } else {
        return audioStreamHolder->getWaveMaker<int>().getAmpTime(index, samplesPerSecond);
    }
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getFreqTime(JNIEnv *env, jclass clazz, jint index,
                                                     jint samplesPerSecond) {
    if (audioStreamHolder->audioDataIsFloat()) {
        return audioStreamHolder->getWaveMaker<float>().getFreqTime(index, samplesPerSecond);
    } else {
        return audioStreamHolder->getWaveMaker<int>().getFreqTime(index, samplesPerSecond);
    }
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_hellooboe_NativeMethods_getSampleRate(JNIEnv *env, jclass clazz) {
    return audioStreamHolder->getSampleRate();
}extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_NativeMethods_setData(JNIEnv *env, jclass clazz, jint row, jint col,
                                                 jdouble start, jdouble end, jdouble length,
                                                 jdouble cycles, jdouble min, jdouble max) {
    bool isAmp;
    if(row == 0){
        isAmp = true;
    } else if(row == 1){
        isAmp = false;
    } else {
        throw std::range_error("row passed to setData() must be 0 or 1");

    }
    if(audioStreamHolder->audioDataIsFloat()) {
        audioStreamHolder->getWaveMaker<float>().setData(isAmp, col, start, end, length, cycles, min, max);
    } else {
        audioStreamHolder->getWaveMaker<int16_t >().setData(isAmp, col, start, end, length, cycles, min, max);
    }
}