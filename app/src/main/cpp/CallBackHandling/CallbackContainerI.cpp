//
// Created by Al on 7/2/2020.
//

#include "CallbackContainerI.h"

void CallbackContainerI::insert(std::vector<int16_t> pushData) {
    data.insert(data.end(), pushData.begin(), pushData.end());
}

std::vector<int16_t> CallbackContainerI::get(int frames) {
    std::vector<int16_t> out{};
    int sub = 0;
    if (data.size() < frames) {
        sub = frames - data.size();
    }
    for (int i = 0; i < frames - sub; i++) {
        out.push_back(data[i]);
    }
    for (int i = 0; i < sub; i++) {
        out.push_back(0);
    }
    data.erase(data.begin(), data.begin() + frames - sub);
    return out;
}