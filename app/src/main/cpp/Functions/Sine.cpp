//
// Created by Al on 7/5/2020.
//

#include <android/log.h>
#include "Sine.h"
#include <cmath>

// TODO figure out how to go from 0 to 0
// TODO figure out how to scale results between max and min while preserving start and end values

template<class T>
Sine<T>::Sine(std::pair<double, double> p0, std::pair<double, double> p1) {
    double x0 = p0.first;
    double y0 = p0.second;
    double x1 = p1.first;
    double y1 = p1.second;
    if ((std::sin(x0)) == (std::sin(x1))) {
        throw std::logic_error(
                "pairs passed to Sine constructor can't be solved due to both first values being 0");
    }
    addPoints({p0, p1});
    double min = std::min<double>({y0, y1});
    double max = std::max<double>({y0, y1});
    setMinY(min);
    setMaxY(max);
    if ((std::sin(x0)) - (std::sin(x1)) != 0) {
        a = (y0 - y1) / (sin(x0) - sin(x1));
    } else {
        a = (y1 - y0) / (sin(x1) - sin(x0));
    }

    b = y0 - (a * sin(x0));
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "a:%.20f", a);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "b:%.20f", b);
}

template<class T>
double Sine<T>::fun(double x) {
    return a * std::sin(x) + b;
}