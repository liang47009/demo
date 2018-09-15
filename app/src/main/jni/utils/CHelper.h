#ifndef _CHELPER_H
#define _CHELPER_H

#include <vector>
#include <android/asset_manager.h>

#include <android/log.h>

#define  LOGI(...) __android_log_print(ANDROID_LOG_INFO, "APP", __VA_ARGS__)
#define  LOGE(...) __android_log_print(ANDROID_LOG_ERROR, "APP", __VA_ARGS__)

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
