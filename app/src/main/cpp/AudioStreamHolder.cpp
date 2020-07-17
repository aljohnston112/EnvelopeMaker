//
// Created by Al on 6/29/2020.
//
#include "DataGeneration/SineWave.h"
#include "AudioStreamHolder.h"
#include "Functions/Linear.h"
#include "Functions/Quadratic.h"
#include <jni.h>
#include <string>
#include <unistd.h>
#include <android/log.h>
#include <utility>
#include <vector>

AudioStreamHolder *as;

extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_NativeMethods_createStream(JNIEnv *env, jclass thiz) {
    as = new AudioStreamHolder();
}
extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_NativeMethods_destroyStream(JNIEnv *env, jclass thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Destroying stream");
    as->requestStopStream();
    as->closeStream();
    delete (as);
}
extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_hellooboe_NativeMethods_audioDataIsFloat(JNIEnv *env, jclass thiz) {
    return as->takesFloat();
}
extern "C"
JNIEXPORT jfloatArray JNICALL
Java_com_example_hellooboe_NativeMethods_loadDataFloat(JNIEnv *env, jclass thiz) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Loading data");
    jfloatArray ret;
    if (as->takesFloat()) {
        std::vector<float> data = as->LoadData<float>();
        ret = env->NewFloatArray(data.size());
        jsize size = env->GetArrayLength(ret);
        env->SetFloatArrayRegion(ret, 0, size, data.data());
    }
    return ret;
}
extern "C"
JNIEXPORT jshortArray JNICALL
Java_com_example_hellooboe_NativeMethods_loadDataShort(JNIEnv *env, jclass thiz) {
    jshortArray ret;
    if (!as->takesFloat()) {
        std::vector<int16_t> data = as->LoadData<int16_t>();
        ret = env->NewShortArray(data.size());
        jsize size = env->GetArrayLength(ret);
        env->SetShortArrayRegion(ret, 0, size, data.data());
    }
    return ret;
}
extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getMinAmp(JNIEnv *env, jclass thiz) {
    return as->minA;
}
extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getMaxAmp(JNIEnv *env, jclass thiz) {
    return as->maxA;
}
extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getMinFreq(JNIEnv *env, jclass thiz) {
    return as->minF;
}
extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_NativeMethods_getMaxFreq(JNIEnv *env, jclass thiz) {
    return as->maxF;
}

AudioStreamHolder::AudioStreamHolder() {
    initAudioStream();
    managedStream->requestStart();
}

AudioStreamHolder::~AudioStreamHolder() {
    managedStream->requestStop();
}

void AudioStreamHolder::initAudioStream() {
    oboe::AudioStreamBuilder builder{};
    builder.setDirection(oboe::Direction::Output);
    builder.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    builder.setSharingMode(oboe::SharingMode::Exclusive);
    builder.setFormat(oboe::AudioFormat::Float);
    builder.setChannelCount(oboe::ChannelCount::Mono);
    builder.setCallback(&callback);
    oboe::Result result = builder.openManagedStream(managedStream);
    if (result != oboe::Result::OK) {
        __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Failed to create stream");
    } else {
        __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Created stream");
    }
    int bytePerSample = managedStream->getBytesPerSample();
    isFloat = bytePerSample != 2;
}