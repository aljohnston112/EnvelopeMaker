//
// Created by Al on 7/4/2020.
//

#include "Exponential.h"
#include <cmath>
#include <android/log.h>

Exponential::Exponential(std::pair<double, double> p0, std::pair<double, double> p1) {
    double x0 = p0.first;
    double y0 = p0.second;
    double x1 = p1.first;
    double y1 = p1.second;
    if (((y1 == 0) && (y0 == 0)) || ((x1 == 0) && (x0 == 0)) || (x1 == x0)) {
        throw std::logic_error(
                "pairs passed to Exponential constructor can't be solve due to both x values or both y values being 0");
    }
    a = (y0 - y1) / (std::exp(x0) - std::exp(x1));
    b = y0 - (a * std::exp(x0));
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "a:%.20f", a);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "b:%.20f", b);
}

double Exponential::f(double x) {
    return (a * exp(x)) + b;
}