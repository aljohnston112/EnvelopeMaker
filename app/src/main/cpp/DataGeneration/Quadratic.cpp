//
// Created by Al on 7/3/2020.
//

#include "Quadratic.h"
#include <exception>
#include <android/log.h>

Quadratic::Quadratic(std::pair<double, double> p0,
                     std::pair<double, double> vertex) {
    double x0 = p0.first;
    double y0 = p0.second;
    double xv = vertex.first;
    double yv = vertex.second;
    if ((3.0*xv*xv) == ((x0)*(x0+(2.0*xv)))) {
        throw std::logic_error(
                "pairs passed to Quadratic constructor must not have the same first values");
    }
    a = (yv-y0)/((xv*xv)-(x0*x0)+((2.0*xv)*(x0-xv)));
    b = -2.0*a*xv;
    c = y0 - (a * (x0 * x0)) - (b * x0);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "a:%.20f", a);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "b:%.20f", b);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "c:%.20f", c);
}

double Quadratic::f(double x) {
    return (a * x * x) + (b * x) + c;
}