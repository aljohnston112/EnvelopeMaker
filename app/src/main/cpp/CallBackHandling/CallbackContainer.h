#ifndef HELLOOBOE_CALLBACKCONTAINERF_H
#define HELLOOBOE_CALLBACKCONTAINERF_H

#include <vector>

struct CallbackContainer {

    CallbackContainer() : data() {};

    void insert(std::vector<float> pushData);

    std::vector<float> get(int frames);

    void clear(){data.clear();};

private:
    std::vector<float> data;
};

#endif //HELLOOBOE_CALLBACKCONTAINERF_H
