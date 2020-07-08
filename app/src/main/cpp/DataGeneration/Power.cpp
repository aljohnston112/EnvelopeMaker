//
// Created by Al on 7/5/2020.
//

#include "Power.h"
#include <cmath>
#include <android/log.h>

// TODO Find a way to make (0, 0) and (x1, 0) work

Power::Power(std::pair<double, double> p0, std::pair<double, double> p1) {
    double x0 = p0.first;
    double y0 = p0.second;
    double x1 = p1.first;
    double y1 = p1.second;
    if (((x0 == 0) && (x1 == 0)) || (y0 == 0) || (y1 == 0)) {
        throw std::logic_error(
                "pairs passed to Power constructor can't be solved due to both first values being 0 or a second values being 0");
    }
    if ((x0 != 0)) {
        a = pow(pow(y0, x0), (1.0 / (x0 * y1)));
    } else {
        a = pow(pow(y1, x1), (1.0 / (x1 * y0)));
    }
    if (x0 != 1) {
        b = y0 - (a * std::log(x0));
    } else {
        b = y1 = (a * std::log(x1));
    }
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "a:%.20f", a);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "b:%.20f", b);
}

double Power::f(double x) {
    return pow(a, x * b);
}