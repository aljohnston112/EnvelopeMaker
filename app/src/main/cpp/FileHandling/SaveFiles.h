//
// Created by Al on 7/8/2020.
//

#ifndef HELLOOBOE_SAVEFILES_H
#define HELLOOBOE_SAVEFILES_H

#include <map>
#include <string>
#include "../DataGeneration/WaveMaker.h"

struct SaveFiles {
    bool insert(std::string name, WaveMaker env) {
        if (envs.find(name) == envs.end()) {
            envs.insert(std::pair{name, env});
            return true;
        }
        return false;
    };
private:
    std::map<std::string, WaveMaker> envs{};
};

#endif //HELLOOBOE_SAVEFILES_H
