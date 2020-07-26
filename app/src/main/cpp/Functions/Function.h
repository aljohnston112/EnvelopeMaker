//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_FUNCTION_H
#define HELLOOBOE_FUNCTION_H

#include <vector>
#include <stdexcept>

struct Function {

    Function(): points(), max(std::numeric_limits<double>::min()), min(std::numeric_limits<double>::max()){};

    template <typename T>
    std::vector<T> fun(double x0, double x1, int points){
        double dx = (x1 - x0) / (points - 1);
        std::vector<T> out{};
        max = std::numeric_limits<double>::min();
        min = std::numeric_limits<double>::max();
            double t;
            for (int i = 0; i < points; i++) {
                t = fun(x0 + (i * dx));
                min = std::min<double>(t, min);
                max = std::max<double>(t, max);
                out.push_back(t);
            }
        return out;
    }

    virtual double fun(double x) = 0;

    void setMax(double mmax) { if (mmax > max) { max = mmax; }};

    void setMin(double mmin) { if (mmin < min) { min = mmin; }};

    double getMax() { return max; };

    double getMin() { return min; };

    void addPoint(std::pair<double, double> point) { points.push_back(point); };

    void addPoints(std::vector<std::pair<double, double>> ppoints) {
        for (auto p : ppoints) {
            addPoint(p);
        }
    };

    std::vector<std::pair<double, double>> *getPoints() { return &points; };

private:
    std::vector<std::pair<double, double>> points;
    double max;
    double min;
};

#endif //HELLOOBOE_FUNCTION_H
