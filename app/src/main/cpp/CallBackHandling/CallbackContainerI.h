//
// Created by Al on 7/2/2020.
//

#ifndef HELLOOBOE_CALLBACKCONTAINERI_H
#define HELLOOBOE_CALLBACKCONTAINERI_H

#include <vector>

struct CallbackContainerI {
    CallbackContainerI() : data() {};

    void insert(std::vector<int16_t> pushData);

    std::vector<int16_t> get(int frames);

private:
    std::vector<int16_t> data;
};

#endif //HELLOOBOE_CALLBACKCONTAINERI_H
