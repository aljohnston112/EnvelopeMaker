#include "CallbackContainer.h"

void CallbackContainer::insert(std::vector<float> pushData) {
    data.insert(data.end(), pushData.begin(), pushData.end());
}

std::vector<float> CallbackContainer::get(int frames) {
    std::vector<float> out{};
    int sub = 0;
    if (data.size() < frames) {
        sub = frames - data.size();
    }

    if (data.size() != 0) {
        for (int i = 0; i < frames - sub; i++) {
            out.push_back(data[i]);
        }
        for (int i = 0; i < sub; i++) {
            out.push_back(0);
        }
        data.erase(data.begin(), data.begin() + frames - sub);
    }
    return out;
}

