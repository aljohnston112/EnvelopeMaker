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
    managedStream->requestStart();
}

AudioStreamHolder::~AudioStreamHolder() {
    managedStream->requestStop();
    managedStream->close();
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Destroyed stream");
}

void AudioStreamHolder::initAudioStream(){
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
}

extern "C"
JNIEXPORT jfloatArray JNICALL
Java_com_example_hellooboe_NativeMethods_loadConstant(JNIEnv *env, jclass clazz, jdouble start,
                                                      jdouble length, jint row,
                                                      jint col) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Loading constant");
    // x1, x2, numPoints
    Constant constant{start};
    std::vector<float> data{
            constant.Function::fun<float>(0, 1, (length * audioStreamHolder->getSampleRate()))};
    if (row == 0) {
        AmplitudeEnvelope amplitudeEnvelope{constant, data};
        audioStreamHolder->getWaveMaker<float>().insert(amplitudeEnvelope, col);
    } else if (row == 1) {
        FrequencyEnvelope frequencyEnvelope{constant, data};
        audioStreamHolder->getWaveMaker<float>().insert(frequencyEnvelope, col);
    }
    jfloatArray outData;
    if (audioStreamHolder->audioDataIsFloat()) {
        outData = env->NewFloatArray(data.size());
        jsize size = env->GetArrayLength(outData);
        env->SetFloatArrayRegion(outData, 0, size, data.data());
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
    if(audioStreamHolder->audioDataIsFloat()) {
        audioStreamHolder->loadData<float>();
    } else{
        audioStreamHolder->loadData<int16_t>();
    }
}