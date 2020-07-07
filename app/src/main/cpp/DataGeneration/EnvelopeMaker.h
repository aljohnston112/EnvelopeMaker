//
// Created by Al on 7/7/2020.
//

#ifndef HELLOOBOE_ENVELOPEMAKER_H
#define HELLOOBOE_ENVELOPEMAKER_H

#include "AmplitudeEnvelope.h"
#include "FrequencyEnvelope.h"

#include <list>
#include <vector>

struct EnvelopeMaker {

    void push_back(AmplitudeEnvelope ae){amps.push_back(ae);};
    void push_back(FrequencyEnvelope ae){freqs.push_back(ae);};
    void erase(std::list<AmplitudeEnvelope>::iterator index){amps.erase(index);}
    void erase(std::list<FrequencyEnvelope>::iterator index){freqs.erase(index);}
    std::vector<float> make(int index);
private:
    std::list<AmplitudeEnvelope> amps{};
    std::list<FrequencyEnvelope> freqs{};
};


#endif //HELLOOBOE_ENVELOPEMAKER_H
