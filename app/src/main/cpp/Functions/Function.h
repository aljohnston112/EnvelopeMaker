//
// Created by Al on 7/3/2020.
//

#ifndef HELLOOBOE_FUNCTION_H
#define HELLOOBOE_FUNCTION_H

#include <vector>

struct Function {

    std::vector<double> f(double x0, double x1, int points);

    virtual double f(double x) = 0;

    void setMax(double mmax){if(mmax > max){max = mmax;}};
    void setMin(double mmin){if(mmin < min){min = mmin;}};

    double getMax(){return max;};
    double getMin(){return min;};

    void addPoint(std::pair<double, double> point){points.push_back(point);};
    void addPoints(std::vector<std::pair<double, double>> ppoints){for(auto p : ppoints){points.push_back(p);}};

    const std::vector<std::pair<double, double>> const * getPoints() const {return &points;};

private:
    std::vector<std::pair<double, double>> points{};
    double max = 0;
    double min = 0;
};

#endif //HELLOOBOE_FUNCTION_H
