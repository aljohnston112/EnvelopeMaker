//
// Created by Al on 7/5/2020.
//

#include "Logarithm.h"
#include <cmath>
#include <android/log.h>

// TODO Find a way to make (0, 0) work
// Let user pick start value, shift to (1, start value) and shift end value to (time+1, end value)
// Then map back to 0-time

template <typename T>
Logarithm<T>::Logarithm(std::pair<double, double> p0, std::pair<double, double> p1) {
    double x0 = p0.first;
    double y0 = p0.second;
    double x1 = p1.first;
    double y1 = p1.second;
    if (x0 == x1) {
        throw std::logic_error(
                "pairs passed to Logarithm constructor can't be solved due to first values being equal");
    }
    if ((x0 == 0) || (x1 == 0)) {
        throw std::logic_error(
                "pairs passed to Logarithm constructor can't be solved due to a first value being 0");
    }
    addPoints({p0, p1});
    double min = std::min<double>({y0, y1});
    double max = std::max<double>({y0, y1});
    setMinY(min);
    setMaxY(max);
    if (x1 != 0) {
        a = (y0 - y1) / std::log((x0 / x1));
    } else {
        a = (y1 - y0) / std::log((x1 / x0));
    }

    if (x0 != 1) {
        b = y0 - (a * std::log(x0));
    } else {
        b = y1 = (a * std::log(x1));
    }
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "a:%.20f", a);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "b:%.20f", b);
}

template<class T>
double Logarithm<T>::fun(double x) {
    return a * log(x) + b;
}