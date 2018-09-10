#ifndef _CHELPER_H
#define _CHELPER_H

#include <vector>
#include <android/asset_manager.h>

#ifdef __cplusplus
extern "C" {
#endif

namespace CHelper {

    bool ReadFile(AAssetManager *, const char *, std::vector<unsigned char> *);

}

#ifdef __cplusplus
}
#endif

#endif /* _CHELPER_H */
