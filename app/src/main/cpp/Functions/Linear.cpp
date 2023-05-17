//
// Created by Al on 7/3/2020.
//

#include "Linear.h"
#include <exception>

template<class T>
Linear<T>::Linear(std::pair<double, double> p0, std::pair<double, double> p1) {
    if (p0.first == p1.first) {
        throw std::logic_error(
                "pairs passed to Linear constructor must not have the same first values");
    }
    addPoints({p0, p1});
    double min = std::min<double>({p0.second, p1.second});
    double max = std::max<double>({p0.second, p1.second});
    setMinY(min);
    setMaxY(max);
    if ((p0.first - p1.first) == 0) {
        a = (p1.second - p0.second) / (p1.first - p0.first);
    } else {
        a = (p0.second - p1.second) / (p0.first - p1.first);
    }
    b = p0.second - (a * p0.first);
}

template<class T>
double Linear<T>::fun(double x) {
    return (a * x) + b;
}