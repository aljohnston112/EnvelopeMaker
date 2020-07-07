//
// Created by Al on 7/2/2020.
//

#ifndef HELLOOBOE_CALLBACKCONTAINERF_H
#define HELLOOBOE_CALLBACKCONTAINERF_H

#include <vector>

struct CallbackContainerF {
    void insert(std::vector<float> pushData);

    std::vector<float> get(int frames);

private:
    std::vector<float> data;
};

#endif //HELLOOBOE_CALLBACKCONTAINERF_H
