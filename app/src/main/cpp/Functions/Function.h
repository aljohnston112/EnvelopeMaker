//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_FUNCTION_H
#define HELLOOBOE_FUNCTION_H

#include <vector>
#include <stdexcept>

template <typename T>
struct Function {

    Function(double start, double end, double length, int sampleRate)
    : mstart(start), mend(end), mlength(length), msampleRate(sampleRate){};

    Function(double start, double end, double cycles, double min, double max, double length, int sampleRate)
            : mstart(start), mend(end), mcycles(cycles), mmin(min), mmax(max), mlength(length), msampleRate(sampleRate){};

    /**
    std::vector<T> fun(double x0, double x1, int points){
        double dx = (x1 - x0) / (points - 1);
        std::vector<T> out{};
        maxY = std::numeric_limits<double>::min();
        minY = std::numeric_limits<double>::max();
            double t;
            for (int i = 0; i < points; i++) {
                t = fun(x0 + (i * dx));
                minY = std::min<double>(t, minY);
                maxY = std::max<double>(t, maxY);
                out.push_back(t);
            }
        return out;
    }
     */

    virtual double fun(double x) = 0;

    double getMaxY() { return maxY; };

    double getMinY() { return minY; };

    std::vector<T>* getData(){return &data;};

    double getTime() {
        return ((double) data.size() / (double) msampleRate);
    }

private:
    std::vector<T> data;

protected:
    double mstart = -1;
    double mend = -1;
    double mlength = -1;
    double mcycles = -1;
    double mmin = -1;
    double mmax = -1;
    int msampleRate = -1;

    double minY = std::numeric_limits<double>::max();
    double maxY = std::numeric_limits<double>::min();
};

#endif //HELLOOBOE_FUNCTION_H
