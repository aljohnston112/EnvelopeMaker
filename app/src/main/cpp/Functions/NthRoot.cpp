//
// Created by Al on 7/4/2020.
//

#include "NthRoot.h"
#include <cmath>
#include <android/log.h>

// TODO Find a way to make (0, y0) and (x1, 0) work
// For (0, y0) subtract y0 for (

template <typename T>
NthRoot<T>::NthRoot(std::pair<double, double> p0, std::pair<double, double> p1) {
    double x0 = p0.first;
    double y0 = p0.second;
    double x1 = p1.first;
    double y1 = p1.second;
    if ((x1 == 0) || (y1 == 0) || (x0 == 0) || (y0 == 0)) {
        throw std::logic_error(
                "pairs passed to NthRoot constructor can't be solve due to 0");
    }
    if ((y0 == y1) || (x0 == x1)) {
        throw std::logic_error(
                "pairs passed to NthRoot constructor can't be solve due to equal first or second values");
    }
    addPoints({p0, p1});
    auto min = std::min<double>({y0, y1});
    auto max = std::max<double>({y0, y1});
    setMinY(min);
    setMaxY(max);
    b = std::log(x0 / x1) / std::log(y0 / y1);
    a = y0 / std::pow(x0, (1.0 / b));
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "a:%.20f", a);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "b:%.20f", b);
}

template <typename T>
double NthRoot<T>::fun(double x) {
    return a * pow(x, (1.0 / b));
}