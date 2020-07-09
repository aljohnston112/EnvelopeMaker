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
Java_com_example_hellooboe_TitleActivity_createStream(JNIEnv *env, jobject /*this*/) {
    as = new AudioStreamHolder();
}

extern "C"
JNIEXPORT void JNICALL
Java_com_example_hellooboe_TitleActivity_destroyStream(JNIEnv *env, jobject /*this*/) {
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Destroying stream");
    as->getManagedStream()->requestStop();
    as->getManagedStream()->close();
    delete (as);
}

extern "C"
JNIEXPORT jboolean JNICALL
Java_com_example_hellooboe_TitleActivity_isFloat(JNIEnv *env, jobject thiz) {
    return as->takesFloat();
}

extern "C"
JNIEXPORT jfloatArray  JNICALL
Java_com_example_hellooboe_TitleActivity_loadDataFloat(JNIEnv *env, jobject /*this*/ ma) {
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
Java_com_example_hellooboe_TitleActivity_loadDataShort(JNIEnv *env, jobject thiz) {
    jshortArray ret;
    if (!as->takesFloat()) {
        std::vector<int16_t> data = as->LoadData<int16_t >();
        ret = env->NewShortArray(data.size());
        jsize size = env->GetArrayLength(ret);
        env->SetShortArrayRegion(ret, 0, size, data.data());
    }
    return ret;
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_TitleActivity_getMin(JNIEnv *env, jobject thiz) {
    // TODO: implement getMin()
}

extern "C"
JNIEXPORT jdouble JNICALL
Java_com_example_hellooboe_TitleActivity_getMax(JNIEnv *env, jobject thiz) {
    // TODO: implement getMax()
}

AudioStreamHolder::AudioStreamHolder() {
    initAudioStream(builder);
    managedStream->requestStart();
}

void AudioStreamHolder::initAudioStream(oboe::AudioStreamBuilder asb) {
    asb.setDirection(oboe::Direction::Output);
    asb.setPerformanceMode(oboe::PerformanceMode::LowLatency);
    asb.setSharingMode(oboe::SharingMode::Exclusive);
    asb.setFormat(oboe::AudioFormat::Float);
    asb.setChannelCount(oboe::ChannelCount::Mono);
    asb.setCallback(&callback);
    oboe::Result result = asb.openManagedStream(managedStream);

    if (result != oboe::Result::OK) {
        __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Failed to create stream");
    } else {
        __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "%s", "Created stream");
    }
    int bytePerSample = managedStream->getBytesPerSample();
    isFloat = bytePerSample != 2;
}