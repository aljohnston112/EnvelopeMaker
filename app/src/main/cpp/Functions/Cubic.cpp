//
// Created by Al on 7/4/2020.
//

#include <android/log.h>
#include <list>
#include "Cubic.h"

Cubic::Cubic(std::pair<double, double> p0, std::pair<double, double> vertex,
             std::pair<double, double> vertex2) {
    double x0 = p0.first;
    double y0 = p0.second;
    double xv0 = vertex.first;
    double yv0 = vertex.second;
    double xv1 = vertex2.first;
    double yv1 = vertex2.second;
    if (((x0 * x0 * xv1) + (xv0 * xv0 * x0) + (xv1 * xv1 * xv0)) ==
        ((xv0 * xv0 * xv1) + (x0 * x0 * xv0) + (xv1 * xv1 * x0))) {
        throw std::logic_error(
                "pairs passed to Cubic constructor must not have the same first values");
    }
    addPoints({p0, vertex, vertex2});
    double min = std::min<double>({y0, yv0, yv1});
    double max = std::max<double>({y0, yv0, yv1});
    setMin(min);
    setMax(max);
    double m = ((x0 * x0 * xv1) + (xv0 * xv0 * x0) + (xv1 * xv1 * xv0)) -
               ((xv0 * xv0 * xv1) + (x0 * x0 * xv0) + (xv1 * xv1 * x0));
    //TODO lots of algebra
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "a:%.20f", a);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "b:%.20f", b);
    __android_log_print(ANDROID_LOG_ERROR, "TRACKERS", "c:%.20f", c);
}

double Cubic::fun(double x) {
    return (a * x * x * x) + (b * x * x) + (c * x) + d;
}