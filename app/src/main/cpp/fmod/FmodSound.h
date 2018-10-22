//
// Created by xll on 2018/10/22.
//

#ifndef DEMO_FMODSOUND_H
#define DEMO_FMODSOUND_H

#include "../common/Singleton.h"

class FmodSound : public Singleton<FmodSound> {
public:
    void start();
};


#endif //DEMO_FMODSOUND_H
